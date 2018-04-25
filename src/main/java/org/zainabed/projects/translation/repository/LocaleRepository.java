package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zainabed.projects.translation.model.Locale;

public interface LocaleRepository extends JpaRepository<Locale, Long> {

}
