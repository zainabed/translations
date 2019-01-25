package org.zainabed.projects.translation.repository.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.service.KeyService;

@RepositoryEventHandler
public class KeyRepositoryEventHandler {

    @Autowired
    KeyService keyService;

    @HandleAfterSave
    public void update(Key key){
        keyService.updateChild(key);
    }

}
