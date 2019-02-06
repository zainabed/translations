package org.zainabed.projects.translation.controller;

import org.hibernate.mapping.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zainabed.projects.translation.export.TranslationExporter;
import org.zainabed.projects.translation.export.TranslationExporterFactory;
import org.zainabed.projects.translation.model.*;
import org.zainabed.projects.translation.repository.LocaleRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;
import org.zainabed.projects.translation.repository.TranslationRepository;
import org.zainabed.projects.translation.service.ProjectService;
import org.zainabed.projects.translation.service.TranslationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectRepository repository;

    @Autowired
    LocaleRepository localeRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    TranslationService translationService;

    @Autowired
    private TranslationRepository translationRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}/locales/{localeId}")
    public void deleteLocale(@PathVariable("id") Long id, @PathVariable("localeId") Long localeId) {
        Project project = repository.getOne(id);
        Locale locale = localeRepository.getOne(localeId);
        project.getLocales().remove(locale);
        repository.save(project);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/{id}/locales/{localeId}")
    public void addLocale(@PathVariable("id") Long id, @PathVariable("localeId") Long localeId) {
        Project project = repository.getOne(id);
        Locale locale = localeRepository.getOne(localeId);
        project.getLocales().add(locale);
        repository.save(project);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path = "/{id}/locales/{localeId}/export/{type}")
    public String export(@PathVariable("id") Long projectId, 
    		@PathVariable("localeId") Long localeId, 
    		@PathVariable("type") String type) {
    	Locale locale = localeRepository.getOne(localeId);
        List<Translation> translations = translationRepository.findAllByLocalesIdAndProjectsId(localeId, projectId);
        TranslationExporter translationExporter = TranslationExporterFactory.get(type);
        return translationExporter.export(translations, locale.getCode());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/{id}/extend/{extend}")
    public void extend(@PathVariable("id") Long projectId, @PathVariable("extend") Long extendProjectId) {
        projectService.extendProject(projectId, extendProjectId);
    }


    @PostMapping(path = "/{projectId}/locales/{localeId}/import/uri")
    public void importURI(@PathVariable("projectId") Long projectId, @PathVariable("localeId") Long localeId, @RequestBody TranslationUri translationUri) {
        translationService.importTranslationFromURI(translationUri, projectId, localeId);
    }

}
