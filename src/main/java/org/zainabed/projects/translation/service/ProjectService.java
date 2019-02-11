package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.ProjectRepository;

@Component
@Order(value=1)
public class ProjectService implements ServiceComponent<Long> {

	@Autowired
	private ProjectRepository repository;

	@Autowired
	KeyService keyService;

	@Autowired
	TranslationService translationService;

	@Autowired
	LocaleService localeService;

	public ProjectRepository getRepository() {
		return repository;
	}

	@Override
	public void extend(Long childProjectId, Long parentProjectId) {
		Project project = repository.getOne(childProjectId);
		Project extendProject = repository.getOne(parentProjectId);

		if (project != null && extendProject != null) {
			project.setExtended(extendProject.getId());
			repository.save(project);
		}
	}

	public void deleteOneByLocaleId(Long projectId, Long localeId) {
		Project project = repository.getOne(projectId);
		Locale locale = localeService.getRepository().getOne(localeId);
		project.getLocales().remove(locale);
		repository.save(project);
	}

	public void addLocaleToProject(Long localeId, Long projectId) {
		Project project = repository.getOne(projectId);
		Locale locale = localeService.getRepository().getOne(localeId);
		project.getLocales().add(locale);
		repository.save(project);
	}
}
