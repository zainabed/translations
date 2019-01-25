package org.zainabed.projects.translation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.zainabed.projects.translation.Application;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.TranslationRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TranslationServiceIT {

    @Autowired
    TranslationService translationService;

    Translation testTranslation;

    @Before
    public void setup() {
        testTranslation = translationService.getRepository().getOne(1L);
    }

    @Test
    public void shouldUpdateChildTranslation() {
        translationService.updateChild(testTranslation);
        String content = testTranslation.getContent();
        List<Translation> translations = translationService.getRepository().findAllByExtendedAndStatus(testTranslation.getId(), Translation.STATUS.EXTENDED);
        translations.stream().forEach(t -> {
            assertEquals(content, t.getContent());
        });
    }

}
