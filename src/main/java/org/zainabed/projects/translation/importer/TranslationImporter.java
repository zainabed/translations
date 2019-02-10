package org.zainabed.projects.translation.importer;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.zainabed.projects.translation.model.Translation;

/**
 * <p>
 * This is an entity importer interface design to import {@link MultipartFile}
 * file. It is an abstraction for application allowing to import different type
 * of translation files.
 * </p>
 *
 * <p>
 * It is not applications concern to retrieve right import implementation rather
 * should provide given inputs as {@link MultipartFile}.
 * </p>
 *
 * @author Zainul Shaikh
 */
public interface TranslationImporter {

	/**
	 * Method should transform {@link MultipartFile} object into {@link Map} object.
	 * 
	 * @param MultipartFile
	 *            {@link MultipartFile} file object
	 * @return Map of Strings
	 */
	Map<String, String> imports(MultipartFile file);

}
