package org.zainabed.projects.translation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.event.TranslationEvent;
import org.zainabed.projects.translation.repository.TranslationRepository;

//@RepositoryRestController
//@RequestMapping("translations")
public class TranslationController {

    @Autowired
    TranslationRepository repository;

    Logger logger = LoggerFactory.getLogger(TranslationController.class);

    @PutMapping(path = "/{id}")
    public Translation put(@RequestBody Translation translation) {
        logger.info("Inside Translation controller");
        Translation saveTranslation = repository.save(translation);
        return saveTranslation;
    }
}
