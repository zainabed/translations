package org.zainabed.projects.translation.importer;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author zain
 *
 */
public interface FileImporter {

	/**
	 * 
	 * @param file
	 * @return
	 */
	String save(MultipartFile file);

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	Map<String, String> build(String fileName);

}
