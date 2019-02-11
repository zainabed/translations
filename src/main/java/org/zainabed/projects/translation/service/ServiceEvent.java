package org.zainabed.projects.translation.service;

/**
 *
 * @param <T>
 */
public interface ServiceEvent<T> {

	/**
	 * 
	 * @param model
	 */
	void updateChild(T model);

	/**
	 * 
	 * @param model
	 */
	void addChild(T model);
}
