package org.zainabed.projects.translation.repository.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.event.TranslationEvent;
import org.zainabed.projects.translation.service.TranslationService;

import javax.persistence.PostUpdate;

@RepositoryEventHandler(Translation.class)
public class TranslationRepositoryEvent {


    @Autowired
    TranslationService translationService;

    Logger logger = LoggerFactory.getLogger(TranslationEvent.class);

    @HandleAfterSave
    public void update(Translation translation) {
        logger.info("Inside listener");
        translationService.updateChild(translation);
    }
}
