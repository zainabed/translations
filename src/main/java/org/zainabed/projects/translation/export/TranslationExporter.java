package org.zainabed.projects.translation.export;

import java.util.List;

import org.zainabed.projects.translation.model.Translation;

/**
 * <p>
 * This is an entity exporter interface design to export {@link Translation}
 * entities. It is an abstraction for application allowing to export
 * translations into different set of formats.
 * </p>
 *
 * <p>
 * It is not applications concern to retrieve right export implementation rather
 * should provide given inputs as {@link List} of {@link Translation} objects
 * and expect export file URI from this interface.
 * </p>
 *
 * @author Zainul Shaikh
 */
public interface TranslationExporter {

	public static final String ANDROID_FORMT = "string-xml";
	public static final String JAVA_FORMAT = "property";
	public static final String JSON_FORMAT = "json";
	public static final String NET_FORMAT = "resx";

	/**
	 * Method should export {@link Translation} objects into file system persist
	 * inside application and return file locations as URI for given file name.
	 *
	 * @param translations
	 *            List of {@link Translation} objects
	 * @return Saved file Uri
	 */
	String export(List<Translation> translations, String fileName);
}
