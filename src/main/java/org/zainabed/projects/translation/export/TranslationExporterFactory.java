package org.zainabed.projects.translation.export;

/**
 * 
 * @author zain
 *
 */
public class TranslationExporterFactory {

	public enum TRANSLATION_TYPE {
		JSON, PROPERTY, NET
	};

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static TranslationExporter get(String type) {
		switch (type) {
		case "property":
			return new PropertyTranslationExporter();
		default:
			return null;
		}

	}
}
