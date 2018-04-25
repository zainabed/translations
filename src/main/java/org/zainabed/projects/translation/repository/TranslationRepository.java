package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zainabed.projects.translation.model.Translation;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

}
