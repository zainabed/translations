package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.projection.LocaleView;

import java.util.List;

@RepositoryRestResource(excerptProjection = LocaleView.class)
public interface LocaleRepository extends JpaRepository<Locale, Long> {
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	@Override
	Locale save(Locale entity);

	List<Locale> findByProjects_Id(Long projectsId);

	/*@Query("select l from translation_locale l join translation_project p where p.id = :id")
	List<Locale> findAllByProjectId(@Param("id") Long projectId);*/

	Locale findByCode(@Param("code") String code);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void deleteById(Long aLong);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void delete(Locale locale);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
	void deleteAll();
}
