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
	 * @return
	 */
	String save(String name);
}
