package org.zainabed.projects.translation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zainabed.projects.translation.model.*;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.repository.KeyRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;
import org.zainabed.projects.translation.repository.TranslationRepository;


import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class TranslationService {

    @Autowired
    private TranslationRepository repository;

    @Autowired
    KeyService keyService;

    @Autowired
    ProjectService projectService;

    @Autowired
    LocaleService localeService;

    Logger log = Logger.getLogger(TranslationService.class.getName());

    public TranslationRepository getRepository() {
        return repository;
    }

    public void extendTranslationsFor(Project project, Long extendProjectId) {
        List<Translation> translations = repository.findAllByProjectsId(extendProjectId);
        List<Key> keys = keyService.getRepository().findAllByProjectsId(project.getId());
        Map<Long, Key> keyMap = keys.stream().collect(Collectors.toMap(Key::getExtended, k -> k));
        List<Translation> newTranslations = translations.stream().map(t -> {
            Translation n = new Translation();
            n.setContent(t.getContent());
            n.setLocales(t.getLocales());
            n.setKeys(keyMap.get(t.getKeys().getId()));
            n.setProjects(project);
            n.setExtended(t.getId());
            n.setStatus(Translation.STATUS.EXTENDED);
            return n;
        }).collect(Collectors.toList());

        repository.saveAll(newTranslations);
    }

    public List<Translation> importTranslationFromURI(TranslationUri translationUri, Long projectId, Long localeId) {
        Locale locale = localeService.getRepository().getOne(localeId);
        Project project = projectService.getRepository().getOne(projectId);
        List<Translation> result = null;
        try {

            // Fetch translation from URI
            Map<String, String> remoteTranslations = getTranslationFromURI(translationUri);

            // Convert translation into key array
            List<String> newKeyList = getKeyList(remoteTranslations);

            // fetch keys exist in db
            List<Key> existingKeys = keyService.getRepository().findAllByNameInAndProjectsId(newKeyList, projectId);
            List<String> existingKeyList = keyService.getKeyStringList(existingKeys);

            // Separate out new keys
            newKeyList.removeAll(existingKeyList);

            // Find all translation for existing keys
            List<Translation> existingTranslations = repository.findAllByKeysInAndProjectsIdAndLocalesId(existingKeys, projectId, localeId);
            List<Key> existingTranslationKeys = keyService.getKeyListFromTranslations(existingTranslations);
            List<String> existingTranslationKeyList = keyService.getKeyStringList(existingTranslationKeys);
            existingKeyList.removeAll(existingTranslationKeyList);
            existingKeys = existingKeys.stream().filter(k-> existingKeyList.contains(k.getName())).collect(Collectors.toList());

            // Add translation for existing keys
            List<Translation> existingKeyTranslations = createTranslationsForKeys(existingKeys, remoteTranslations, project, locale);

            // Modify translation according to imported translation
            List<Translation> updatedTranslations = updateTranslationForRemoteTranslation(existingTranslations, remoteTranslations);

            // Generate new translations
            List<Key> remoteKeys = keyService.createNewKeysFromList(newKeyList, project);
            List<Translation> newTranslations = createTranslationsForKeys(remoteKeys, remoteTranslations, project, locale);

            if (newTranslations == null) {
                newTranslations = new ArrayList<>();
            }

            // Merge all translations
            newTranslations.addAll(updatedTranslations);
            newTranslations.addAll(existingKeyTranslations);
            result = repository.saveAll(newTranslations);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
        }

        return result;
    }

    public List<Translation> updateTranslationForRemoteTranslation(List<Translation> translations, Map<String, String> remoteTranslations) {
        return translations.stream().map(t -> {
            t.setContent(remoteTranslations.get(t.getKeys().getName()));
            return t;
        }).collect(Collectors.toList());
    }

    public List<Translation> createTranslationsForKeys( List<Key> remoteKeys , Map<String, String> remoteTranslations, Project project, Locale locale) {

        return remoteKeys.stream().map(k -> {
            Translation translation = new Translation();
            translation.setContent(remoteTranslations.get(k.getName()));
            translation.setKeys(k);
            translation.setProjects(project);
            translation.setLocales(locale);
            return translation;
        }).collect(Collectors.toList());
    }

    public Map<String, String> getTranslationFromURI(TranslationUri translationUri) {
        RestTemplate restTemplate = new RestTemplate();
        String remoteUrl = translationUri.getUri() + "/" + translationUri.getLocale();
        log.info(remoteUrl);
        HashMap<String, String> response = restTemplate.getForObject(remoteUrl, new HashMap<String, String>().getClass());
        log.info(response.toString());
        return response;
    }

    public List<String> getKeyList(Map<String, String> translation) {
        return translation.entrySet().stream().map(m -> m.getKey()).collect(Collectors.toList());
    }

}
