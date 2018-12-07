package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;

@PreAuthorize("hasRole('ROLE_USER')")
public interface LocaleRepository extends JpaRepository<Locale, Long> {
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	@Override
	Locale save(Locale entity);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void deleteById(Long aLong);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void delete(Locale locale);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void deleteAll();
}
