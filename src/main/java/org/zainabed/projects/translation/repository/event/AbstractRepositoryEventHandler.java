package org.zainabed.projects.translation.repository.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.zainabed.projects.translation.service.ServiceEvent;

public class AbstractRepositoryEventHandler<T> implements ServiceEvent<T> {

    @Autowired
    protected ServiceEvent<T> serviceEvent;


    @HandleAfterSave
    @Override
    public void updateChild(T model) {
        serviceEvent.updateChild(model);
    }

    @HandleAfterCreate
    @Override
    public void addChild(T model) {
        serviceEvent.addChild(model);
    }
}
