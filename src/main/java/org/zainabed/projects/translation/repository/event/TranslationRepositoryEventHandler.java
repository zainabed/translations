package org.zainabed.projects.translation.repository.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.event.TranslationEvent;
import org.zainabed.projects.translation.service.TranslationService;

import javax.persistence.PostUpdate;

@RepositoryEventHandler(Translation.class)
public class TranslationRepositoryEventHandler {


    @Autowired
    TranslationService translationService;

    //Logger logger = LoggerFactory.getLogger(TranslationRepositoryEventHandler.class);

    @HandleAfterSave
    public void update(Translation translation) {
        translationService.updateChild(translation);
    }

    @HandleAfterCreate
    public void save(Translation translation) {
        translationService.addChild(translation);
    }
}
