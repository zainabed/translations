package org.zainabed.projects.translation.service;

/**
 *
 * @param <I>
 */
public interface ServiceComponent<I> {

	
	
	/**
	 * 
	 * @param childId
	 * @param parentId
	 */
	void extend(I childId, I parentId);
}
