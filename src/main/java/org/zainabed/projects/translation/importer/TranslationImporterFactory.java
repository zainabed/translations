package org.zainabed.projects.translation.importer;

/**
 * This class is a factory method class to provide different
 * set of Translation importer objects.
 *
 * @author Zainul Shaikh
 */
public abstract class TranslationImporterFactory {
    /**
     * Factory method to instantiate translation importer object according
     * to given file format.
     *
     * @param type File format name
     * @return {@link TranslationImporter} object
     */
    public static TranslationImporter get(String type) {
        switch (type) {
            case TranslationImporter.JAVA_FORMAT:
                return new PropertyTranslationImporter();
            case TranslationImporter.JSON_FORMAT:
                return new JsonTranslationImporter();
            case TranslationImporter.NET_FORMAT:
                return new ResxTranslationImporter();
            case TranslationImporter.ANDROID_FORMAT:
                return new StringXmlTranslationImporter();
            default:
                return null;
        }

    }
}
