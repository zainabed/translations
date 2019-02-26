package org.zainabed.projects.translation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zainabed.projects.translation.export.TranslationExporter;
import org.zainabed.projects.translation.export.TranslationExporterFactory;
import org.zainabed.projects.translation.importer.TranslationImporter;
import org.zainabed.projects.translation.importer.TranslationImporterFactory;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.TranslationUri;
import org.zainabed.projects.translation.service.KeyService;
import org.zainabed.projects.translation.service.LocaleService;
import org.zainabed.projects.translation.service.ProjectService;
import org.zainabed.projects.translation.service.ServiceComponent;
import org.zainabed.projects.translation.service.ServiceComposite;
import org.zainabed.projects.translation.service.TranslationService;

/**
 * Rest controller for {@link Project} entity model.
 * It is design to provide additional Rest APIs which could not be produced from common
 * Spring Data JPA Rest services for {@link Project} model.
 *
 * @author Zainul Shaikh
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {


    @Autowired
    LocaleService localeService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TranslationService translationService;

    @Autowired
    ServiceComposite serviceComposite;

    /**
     * Method deletes given {@link Locale} object from given {@link Project} object.
     * Here we are updating reference link of entities that is why it can't be done
     * throw JPA methods.
     * Only Admin is allowed to perform this operation.
     *
     * @param projectId Project id
     * @param localeId  Locale id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}/locales/{localeId}")
    public void deleteLocale(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId) {
        projectService.deleteOneByLocaleId(projectId, localeId);
    }

    /**
     * Method add given {@link Locale} to given {@link Project} object.
     * Here we are adding new reference link of entities that is why it can't be done
     * throw JPA methods.
     * Only Admin is allowed to perform this operation.
     *
     * @param projectId Project id
     * @param localeId  Locale id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/{id}/locales/{localeId}")
    public void addLocale(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId) {
        projectService.addLocaleToProject(localeId, projectId);
    }

    /**
     * Method saves {@link Translation} objects of given {@link Project} for given {@link Locale} into
     * system as File of given format.
     * It returns saved file URI combined with application host URI.
     * Method uses {@link TranslationExporter} object to perform export functionality.
     *
     * @param projectId Project Id
     * @param localeId  Locale Id
     * @param type      File format type
     * @param request   {@link HttpServletRequest} object
     * @return Export file URI
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path = "/{id}/locales/{localeId}/export/{type}", produces = {"text/plain"})
    public String export(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId,
                         @PathVariable("type") String type, HttpServletRequest request) {
        return translationService.doExport(projectId, localeId, type, request);
    }

    /**
     * This methods imports {@link Translation} objects given {@link MultipartFile} file of given file type
     * and persist it into database.
     * It uses {@link TranslationImporter} object to import translations from given file and transform content
     * into {@link Map} key value pairs, later {@link TranslationService} then save into database.
     *
     * @param projectId Project Id
     * @param localeId  Locale Id
     * @param type      File format type
     * @param file      {@link MultipartFile} file to import
     * @return Imported file URI
     */
    @PostMapping(path = "/{projectId}/locales/{localeId}/import/{type}")
    public List<Translation> importer(@PathVariable("projectId") Long projectId,
                                      @PathVariable("localeId") Long localeId, @PathVariable("type") String type,
                                      @RequestParam("file") MultipartFile file) {
        return translationService.doImport(projectId, localeId, type, file);
    }

    /**
     * Method is used to extend one project from another. functionality is abstracted into {@link ServiceComposite}
     * and it server extend method through each {@link ServiceComponent} objects e.g  {@link ProjectService},
     * {@link LocaleService}, {@link KeyService} and {@link TranslationService}.
     *
     * @param projectId       Project Id
     * @param extendProjectId Project to be extended
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/{id}/extend/{extend}")
    public void extend(@PathVariable("id") Long projectId, @PathVariable("extend") Long extendProjectId) {
        serviceComposite.extend(projectId, extendProjectId);
    }

    /**
     * Deprecated method to import translation form URI
     *
     * @param projectId
     * @param localeId
     * @param translationUri
     */
    @Deprecated
    @PostMapping(path = "/{projectId}/locales/{localeId}/import-uri")
    public void importURI(@PathVariable("projectId") Long projectId, @PathVariable("localeId") Long localeId,
                          @RequestBody TranslationUri translationUri) {

    }


}
