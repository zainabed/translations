package org.zainabed.projects.translation.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zain
 */
@Component
public class ServiceComposite implements ServiceComponent<Long> {

	Logger logger = Logger.getLogger(ServiceComposite.class.getName());

	/*
	 *
	 */
	@Autowired
	protected List<ServiceComponent<Long>> serviceComponents;

	/**
	 * @param childId
	 * @param parentId
	 */
	@Override
	public void extend(Long childId, Long parentId) {
		serviceComponents.forEach(m -> m.extend(childId, parentId));
	}
}
