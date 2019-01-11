package org.zainabed.projects.translation.model.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.TranslationRepository;

import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.util.List;
import java.util.stream.Collectors;

public class TranslationEvent {

    @Autowired
    TranslationRepository repository;

    Logger logger = LoggerFactory.getLogger(TranslationEvent.class);

    @PreUpdate
    public void update(Translation translation) {
        List<Translation> translations = repository.findAllByExtended(translation.getId());
        translations.stream().filter(t -> {
            return t.getStatus().equals(Translation.STATUS.EXTENDED);
        }).map(t -> {
            t.setContent(translation.getContent());
            return t;
        }).forEach( t -> {
            logger.info( t.getId() + "`");
        });
       /* translation.setStatus(Translation.STATUS.UPDATED);

        translations.stream().forEach(t -> {
            logger.info(t.getId() + "");
        });*/

        /*if (translations != null) {
            repository.saveAll(translations);
        }*/
        logger.info("Inside listener");
    }
}
