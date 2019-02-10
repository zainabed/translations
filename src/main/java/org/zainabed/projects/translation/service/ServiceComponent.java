package org.zainabed.projects.translation.service;

/**
 * 
 * @author zain
 *
 * @param <T>
 */
public interface ServiceComponent<I> {

	
	
	/**
	 * 
	 * @param childId
	 * @param parentId
	 */
	void extend(I childId, I parentId);
}
