package org.zainabed.projects.translation.model.projection;

import org.springframework.data.rest.core.config.Projection;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Translation;

@Projection(name="view", types=Translation.class)
public interface TranslationView {
	String getContent();

	Locale getLocales();

	Key getKeys();
}
