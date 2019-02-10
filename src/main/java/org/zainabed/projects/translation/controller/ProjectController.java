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
import org.zainabed.projects.translation.service.ProjectService;
import org.zainabed.projects.translation.service.TranslationService;

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

	/**
	 * 
	 * @param id
	 * @param localeId
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(path = "/{id}/locales/{localeId}")
	public void deleteLocale(@PathVariable("id") Long id, @PathVariable("localeId") Long localeId) {
		Project project = repository.getOne(id);
		Locale locale = localeRepository.getOne(localeId);
		project.getLocales().remove(locale);
		repository.save(project);
	}

	/**
	 * 
	 * @param id
	 * @param localeId
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/{id}/locales/{localeId}")
	public void addLocale(@PathVariable("id") Long id, @PathVariable("localeId") Long localeId) {
		Project project = repository.getOne(id);
		Locale locale = localeRepository.getOne(localeId);
		project.getLocales().add(locale);
		repository.save(project);
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
		Locale locale = localeRepository.getOne(localeId);
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
		projectService.extendProject(projectId, extendProjectId);
	}

	/**
	 * 
	 * @param projectId
	 * @param localeId
	 * @param translationUri
	 */
	@PostMapping(path = "/{projectId}/locales/{localeId}/import-uri")
	public void importURI(@PathVariable("projectId") Long projectId, @PathVariable("localeId") Long localeId,
			@RequestBody TranslationUri translationUri

	) {

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
		return translationService.storeTranslations(translations, projectId, localeId);
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
