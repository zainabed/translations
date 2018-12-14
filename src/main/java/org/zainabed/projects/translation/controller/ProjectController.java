package org.zainabed.projects.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.LocaleRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository repository;

	@Autowired
	LocaleRepository localeRepository;

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
}
