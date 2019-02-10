package org.zainabed.projects.translation.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zain
 *
 */
@Component
public class ServiceComposite implements ServiceComponent<Long> {

	Logger logger = Logger.getLogger("ModelComposit");

	/*
	 * 
	 */
	protected List<ServiceComponent<Long>> serviceComponents;

	public void reset() {
		serviceComponents = null;
		serviceComponents = new ArrayList<>();
	}

	/**
	 * 
	 * @param modelComponent
	 */
	public void addServiceComponent(ServiceComponent<Long> modelComponent) {
		serviceComponents.add(modelComponent);
	}

	/**
	 * 
	 */
	@Override
	public void extend(Long childId, Long parentId) {
		serviceComponents.forEach(m -> m.extend(childId, parentId));
	}
}
