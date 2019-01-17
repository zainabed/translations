package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.projection.TranslationView;

import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource(excerptProjection = TranslationView.class)
public interface TranslationRepository extends JpaRepository<Translation, Long> {

	@RestResource(path = "get_one", rel = "translations")
	Translation findByProjectsIdAndLocalesIdAndKeysId(@Param("projects") Long projects, @Param("locales") Long locales,
			@Param("keys") Long keys);

	List<Translation> findAllByLocalesId(@Param("locales") Long id);
	List<Translation> findAllByLocalesIdAndProjectsId(@Param("locales") Long localeId, @Param("projects") Long projectId);
	List<Translation> findAllByProjectsId(@Param("projectsId") Long projectsId);
	List<Translation> findAllByExtendedAndStatus(@Param("extended") Long extended, @Param("status")Translation.STATUS status);
	List<Translation> findAllByExtended(@Param("extended") Long extended);

	List<Translation> findAllByKeysInAndProjectsId(@Param("keys") List<Key> keys, @Param("projects") Long projects);
	List<Translation> findAllByKeysInAndProjectsIdAndLocalesId(@Param("keys") List<Key> keys, @Param("projects") Long projects, @Param("locales") Long locales);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO') or hasRole('ROLE_TRANSLATOR')")
	@Override
	Translation save(Translation entity);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO') or hasRole('ROLE_TRANSLATOR')")
	void deleteById(Long aLong);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO') or hasRole('ROLE_TRANSLATOR')")
	void delete(Translation translation);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO') or hasRole('ROLE_TRANSLATOR')")
	void deleteAll();
}
