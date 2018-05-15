package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.zainabed.projects.translation.model.Translation;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

	@RestResource(path = "get_one", rel = "translations")
	Translation findByProjectsIdAndLocalesIdAndKeysId(@Param("projects") Long projects,
			@Param("locales") Long locales, @Param("keys") Long keys);

}
