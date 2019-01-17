package org.zainabed.projects.translation.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.ProjectRepository;

import java.util.Optional;

@Component
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    public ProjectRepository getRepository() {
        return repository;
    }

    @Autowired
    KeyService keyService;

    @Autowired
    TranslationService translationService;

    @Autowired
    LocaleService localeService;

    public void extendProject(Long projectId, Long extendProjectId) {
        Project project = repository.getOne(projectId);
        Project extendProject = repository.getOne(extendProjectId);

        if (project != null && extendProject != null) {
            project.setExtended(extendProject.getId());
            project = repository.save(project);

            // Extend locales
            localeService.extendLocalesFor(project, extendProject);

            // Extend Keys
            keyService.extendKeysForProjects(project, extendProjectId);

            // Extend translations
            translationService.extendTranslationsFor(project, extendProjectId);
        }
    }
}
