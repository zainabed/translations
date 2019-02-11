package org.zainabed.projects.translation.repository.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.zainabed.projects.translation.service.ModelService;

public class AbstractModelEventHandler<T> implements ModelService<T> {

    @Autowired
    protected ModelService<T> modelService;


    @HandleAfterSave
    @Override
    public void updateChild(T model) {
        modelService.updateChild(model);
    }

    @HandleAfterCreate
    @Override
    public void addChild(T model) {
        modelService.addChild(model);
    }
}
