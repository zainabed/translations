package org.zainabed.projects.translation.export;

/**
 * @author zain
 */
public abstract class TranslationExporterFactory {

    public enum TRANSLATION_TYPE {
        JSON, PROPERTY, NET
    }

    ;

    /**
     * @param type
     * @return
     */
    public static TranslationExporter get(String type) {
        switch (type) {
            case "property":
                return new PropertyTranslationExporter();
            case "json":
                return new JsonTranslationExporter();
            case "resx":
                return new ResxTranslationExporter();
            case "string-xml":
                return new StringsXmlTranslationExporter();
            default:
                return null;
        }

    }
}
