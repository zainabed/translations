package org.zainabed.projects.translation.importer;

public abstract class TranslationImporterFactory {
	/**
	 * @param type
	 * @return
	 */
	public static TranslationImporter get(String type) {
		switch (type) {
		case "property":
			return new PropertyTranslationImporter();
		case "json":
			return new JsonTranslationImporter();
		case "resx":
			return new ResxTranslationImporter();
		case "string-xml":
			return new StringXmlTranslationImporter();
		default:
			return null;
		}

	}
}
