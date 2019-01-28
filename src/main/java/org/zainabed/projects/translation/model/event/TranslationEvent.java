package org.zainabed.projects.translation.model.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.TranslationRepository;
import org.zainabed.projects.translation.service.TranslationService;

import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.util.List;
import java.util.stream.Collectors;

public class TranslationEvent {

    @Autowired
    TranslationService translationService;

    Logger logger = LoggerFactory.getLogger(TranslationEvent.class);

    @PostUpdate
    public void update(Translation translation) {
        logger.info("Inside listener");
        translationService.updateChild(translation);
    }
}
