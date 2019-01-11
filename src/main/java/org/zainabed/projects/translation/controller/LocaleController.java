package org.zainabed.projects.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.LocaleRepository;
import org.zainabed.projects.translation.repository.TranslationRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/locales")
public class LocaleController {

    class KeyValue {
        String key;
        String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String toString() {
            return key + " : " + value;
        }
    }

    @Autowired
    private LocaleRepository repository;

    @Autowired
    private TranslationRepository translationRepository;

    @GetMapping(path = "{id}/download")
    public Map<String, String> download(@PathVariable("id") Long id) throws IOException {
        Optional<Locale> locale = repository.findById(id);
        List<Translation> translations = translationRepository.findAllByLocalesId(id);
        return translations.stream().map(t -> {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(t.getKeys().getName());
            keyValue.setValue(t.getContent());
            return keyValue;
        }).collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));

    }
}
