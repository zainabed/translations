package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.projection.TranslationView;

@RepositoryRestResource(excerptProjection = TranslationView.class)
public interface TranslationRepository extends JpaRepository<Translation, Long> {

	@RestResource(path = "get_one", rel = "translations")
	Translation findByProjectsIdAndLocalesIdAndKeysId(@Param("projects") Long projects, @Param("locales") Long locales,
			@Param("keys") Long keys);

}
