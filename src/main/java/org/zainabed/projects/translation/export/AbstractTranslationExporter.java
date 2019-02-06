package org.zainabed.projects.translation.export;

import java.util.List;

import org.zainabed.projects.translation.model.Translation;

/**
 * 
 * @author zain
 *
 */
public abstract class AbstractTranslationExporter implements TranslationExporter, FileExporter<Translation> {

	
	/**
	 * 
	 * @param models
	 * @return
	 */
	@Override
	public final String export(List<Translation> translations, String fileName) {
		build(translations);
		return save(fileName);
	}

}
