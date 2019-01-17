package org.zainabed.projects.translation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.LocaleRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocaleService {

    @Autowired
    private LocaleRepository repository;

    public LocaleRepository getRepository() {
        return repository;
    }

    @Autowired
    ProjectRepository projectRepository;

    Logger logger = LoggerFactory.getLogger(LocaleService.class);

    public void extendLocalesFor(Project project, Project extendProject) {
        List<Locale> locales = repository.findByProjects_Id(extendProject.getId());
        logger.info(locales.toString());
        locales.stream().forEach(l -> {
            addLocaleToProject(l, project);
        });
    }

    public void addLocaleToProject(Locale locale, Project project) {
        project.getLocales().add(locale);
        projectRepository.save(project);
    }
}
