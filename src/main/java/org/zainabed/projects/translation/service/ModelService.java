package org.zainabed.projects.translation.service;

public interface ModelService<T> {
	
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
