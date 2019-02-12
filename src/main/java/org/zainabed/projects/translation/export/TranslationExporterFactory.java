package org.zainabed.projects.translation.export;

/**
 * This class is a factory method class to provide different
 * set of Translation exporter objects.
 *
 * @author Zainul Shaikh
 */
public abstract class TranslationExporterFactory {

    /**
     * Factory method to instantiate translation object according
     * to given file format.
     *
     * @param type File format
     * @return {@link TranslationExporter} object
     */
    public static TranslationExporter get(String type) {
        switch (type) {
            case TranslationExporter.JAVA_FORMAT:
                return new PropertyTranslationExporter();
            case TranslationExporter.JSON_FORMAT:
                return new JsonTranslationExporter();
            case TranslationExporter.NET_FORMAT:
                return new ResxTranslationExporter();
            case TranslationExporter.ANDROID_FORMAT:
                return new StringsXmlTranslationExporter();
            default:
                return null;
        }

    }
}
