package org.zainabed.projects.translation.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.repository.KeyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
public class KeyServiceTest {

    @Autowired
    KeyService keyService;

    @MockBean
    KeyRepository repository;

    @MockBean
    ProjectService projectService;

    @Configuration
    @Import(KeyService.class)
    public static class Config {
    }

    List<Key> keys;
    List<String> keyNames;

    @BeforeEach
    public void setup() {
        keyNames = Stream.of("test1", "test2", "test3").collect(Collectors.toList());
        keys = keyNames.stream().map(k -> {
            Key key = new Key();
            key.setName(k);
            return key;
        }).collect(Collectors.toList());
    }

    @Test
    public void shouldReturnStringListFromKeys() {
        List<String> keyNameList = keyService.getKeyStringList(keys);
        boolean result = keyNameList.stream().allMatch(k -> keyNames.contains(k));
        assertTrue(result);
    }
}
