package org.zainabed.projects.translation.export;

import java.util.List;

/**
 * 
 * @author zain
 *
 * @param <T>
 */
public interface FileExporter<T> {

	/**
	 * 
	 * @param models
	 */
	void build(List<T> models);

	/**
	 *
	 * @param name
	 * @return
	 */
	String save(String name);

	/**
	 *
	 * @param name
	 * @return
	 */
	String getFileName(String name);

	/**
	 *
	 * @param name
	 * @return
	 */
	String getFilePath(String name);

	/**
	 *
	 * @return
	 */
	String getFileUri();
}
