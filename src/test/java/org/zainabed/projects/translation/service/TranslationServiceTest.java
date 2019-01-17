package org.zainabed.projects.translation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.zainabed.projects.translation.repository.TranslationRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class TranslationServiceTest {

    @Autowired
    TranslationService translationService;

    @MockBean
    private TranslationRepository repository;

    @MockBean
    KeyService keyService;

    @MockBean
    ProjectService projectService;

    @MockBean
    LocaleService localeService;

    Map<String, String> data;
    List<String> keys;

    @Configuration
    @Import(TranslationService.class)
    public static class Config {
    }

    @Before
    public void setup() {
        keys = Stream.of("test1", "test2", "test3").collect(Collectors.toList());
        data = new HashMap<>();
        keys.forEach(k -> {
            data.put(k, k + "value");
        });


    }

    @Test
    public void shouldReturnKeyListFromMapOfStrings() {
        List<String> keyList = translationService.getKeyList(data);
        boolean result = keyList.stream().allMatch(k -> keys.contains(k));
        assertTrue(result);
    }

}
