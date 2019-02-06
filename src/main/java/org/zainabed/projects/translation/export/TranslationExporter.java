package org.zainabed.projects.translation.export;

import java.util.List;

import org.zainabed.projects.translation.model.Translation;

/**
 * 
 * @author zain
 *
 */
public interface TranslationExporter {

	/**
	 * 
	 * @param models
	 * @return
	 */
	String export(List<Translation> models, String fileName);
}
