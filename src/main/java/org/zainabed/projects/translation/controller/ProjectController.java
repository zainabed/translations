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
import org.zainabed.projects.translation.repository.LocaleRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;
import org.zainabed.projects.translation.repository.TranslationRepository;
import org.zainabed.projects.translation.service.KeyService;
import org.zainabed.projects.translation.service.LocaleService;
import org.zainabed.projects.translation.service.ServiceComposite;
import org.zainabed.projects.translation.service.ProjectService;
import org.zainabed.projects.translation.service.TranslationService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository repository;

	@Autowired
	LocaleService localeService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TranslationService translationService;

	@Autowired
	KeyService keyService;

	@Autowired
	private TranslationRepository translationRepository;

	@Autowired
	ServiceComposite serviceComposite;

	/**
	 * 
	 * @param id
	 * @param localeId
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(path = "/{id}/locales/{localeId}")
	public void deleteLocale(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId) {
		projectService.deleteOneByLocaleId(projectId, localeId);
	}

	/**
	 * 
	 * @param id
	 * @param localeId
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/{id}/locales/{localeId}")
	public void addLocale(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId) {
		projectService.addLocaleToProject(localeId, projectId);
	}

	/**
	 * 
	 * @param projectId
	 * @param localeId
	 * @param type
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(path = "/{id}/locales/{localeId}/export/{type}", produces = { "text/plain" })
	public String export(@PathVariable("id") Long projectId, @PathVariable("localeId") Long localeId,
			@PathVariable("type") String type, HttpServletRequest request) {
		Locale locale = localeService.getRepository().getOne(localeId);
		List<Translation> translations = translationRepository.findAllByLocalesIdAndProjectsId(localeId, projectId);
		TranslationExporter translationExporter = TranslationExporterFactory.get(type);
		String exportPath = getHostUri(request, "/projects")
				+ translationExporter.export(translations, locale.getCode());
		return exportPath;
	}

	/**
	 * 
	 * @param projectId
	 * @param extendProjectId
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/{id}/extend/{extend}")
	public void extend(@PathVariable("id") Long projectId, @PathVariable("extend") Long extendProjectId) {
		serviceComposite.reset();
		serviceComposite.addServiceComponent(projectService);
		serviceComposite.addServiceComponent(localeService);
		serviceComposite.addServiceComponent(keyService);
		serviceComposite.addServiceComponent(translationService);
		serviceComposite.extend(projectId, extendProjectId);
	}

	/**
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

	/**
	 * 
	 * @param projectId
	 * @param localeId
	 * @param type
	 * @param file
	 * @return
	 */
	@PostMapping(path = "/{projectId}/locales/{localeId}/import/{type}")
	public List<Translation> importer(@PathVariable("projectId") Long projectId,
			@PathVariable("localeId") Long localeId, @PathVariable("type") String type,
			@RequestParam("file") MultipartFile file) {
		TranslationImporter translationImporter = TranslationImporterFactory.get(type);
		Map<String, String> translations = translationImporter.imports(file);
		return translationService.save(translations, projectId, localeId);
	}

	/**
	 * 
	 * @param request
	 * @param path
	 * @return
	 */
	String getHostUri(HttpServletRequest request, String path) {
		String requestPath = request.getRequestURL().toString();
		return requestPath.substring(0, requestPath.indexOf(path)) + "/";
	}

}
