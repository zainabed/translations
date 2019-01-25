package org.zainabed.projects.translation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.event.TranslationEvent;
import org.zainabed.projects.translation.repository.TranslationRepository;
import org.zainabed.projects.translation.service.TranslationService;

import javax.validation.Valid;

//@RepositoryRestController
//@RequestMapping("translations")
public class TranslationController {

    @Autowired
    TranslationService translationService;

    Logger logger = LoggerFactory.getLogger(TranslationController.class);

    @PutMapping(path = "/{id}")
    public Translation put(@Valid @RequestBody Translation translation) {
        logger.info("Inside Translation controller");
        translation.setStatus(Translation.STATUS.UPDATED);
        Translation saveTranslation = translationService.getRepository().save(translation);
        translationService.updateChild(translation);
        return saveTranslation;
    }
}
