package org.zainabed.projects.translation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.LocaleRepository;

@Component
@Order(value=2)
public class LocaleService implements ServiceComponent<Long> {

	@Autowired
	private LocaleRepository repository;

	@Autowired
	ProjectService projectService;

	Logger logger = LoggerFactory.getLogger(LocaleService.class);

	public LocaleRepository getRepository() {
		return repository;
	}

	@Override
	public void extend(Long childProjectId, Long parentProjectId) {
		Project project = projectService.getRepository().getOne(childProjectId);
		List<Locale> locales = repository.findByProjects_Id(parentProjectId);
		locales.stream().forEach(l -> save(l, project));
	}

	/**
	 * 
	 * @param locale
	 * @param project
	 */
	public void save(Locale locale, Project project) {
		project.getLocales().add(locale);
		projectService.getRepository().save(project);
	}
}
