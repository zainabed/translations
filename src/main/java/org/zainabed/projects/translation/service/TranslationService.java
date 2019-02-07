package org.zainabed.projects.translation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.zainabed.projects.translation.model.BaseModel;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.TranslationUri;
import org.zainabed.projects.translation.repository.TranslationRepository;

@Component
public class TranslationService implements ModelService<Translation> {
	
	Logger logger = Logger.getLogger(TranslationService.class.getName());

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

    /**
     * @param project
     * @param extendProjectId
     */
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

    /**
     * @param translationUri
     * @param projectId
     * @param localeId
     * @return
     */
    public List<Translation> importTranslationFromURI(TranslationUri translationUri, Long projectId, Long localeId) {
        // Fetch translation from URI
        Map<String, String> remoteTranslations = getTranslationFromURI(translationUri);
        return storeTranslations(remoteTranslations, projectId, localeId);
    }

    /**
     *
     * @param translations
     * @param projectId
     * @param localeId
     * @return
     */
    public List<Translation> storeTranslations(Map<String, String> translations, Long projectId, Long localeId) {
        Locale locale = localeService.getRepository().getOne(localeId);
        Project project = projectService.getRepository().getOne(projectId);
        List<Translation> result = null;
        logger.info(translations.toString());
        try {


            // Convert translation into key array
            Set<String> newKeyList = new HashSet<String>(getKeyList(translations));

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
            existingKeys = existingKeys.stream().filter(k -> existingKeyList.contains(k.getName())).collect(Collectors.toList());

            // Add translation for existing keys
            List<Translation> existingKeyTranslations = createTranslationsForKeys(existingKeys, translations, project, locale);

            // Modify translation according to imported translation
            List<Translation> updatedTranslations = updateTranslationForRemoteTranslation(existingTranslations, translations);

            // Generate new translations
            List<Key> remoteKeys = keyService.createNewKeysFromList(newKeyList, project);
            List<Translation> newTranslations = createTranslationsForKeys(remoteKeys, translations, project, locale);

            if (newTranslations == null) {
                newTranslations = new ArrayList<>();
            }

            // Merge all translations
            newTranslations.addAll(updatedTranslations);
            newTranslations.addAll(existingKeyTranslations);
            logger.info(translations.toString());
            result = repository.saveAll(newTranslations);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
        }

        return result;
    }

    /**
     * @param translations
     * @param remoteTranslations
     * @return
     */
    public List<Translation> updateTranslationForRemoteTranslation(List<Translation> translations, Map<String, String> remoteTranslations) {
        return translations.stream().peek(t -> {
            t.setContent(remoteTranslations.get(t.getKeys().getName()));
        }).collect(Collectors.toList());
    }

    /**
     * @param remoteKeys
     * @param remoteTranslations
     * @param project
     * @param locale
     * @return
     */
    public List<Translation> createTranslationsForKeys(List<Key> keys, Map<String, String> translations, Project project, Locale locale) {

        return keys.stream().map(k -> {
            Translation translation = new Translation();
            logger.info(k.getName());
            logger.info("translation =" + translations.get(k.getName()));
            translation.setContent(translations.get(k.getName()));
            translation.setKeys(k);
            translation.setProjects(project);
            translation.setLocales(locale);
            return translation;
        }).collect(Collectors.toList());
    }

    /**
     * @param translationUri
     * @return
     */
    public Map<String, String> getTranslationFromURI(TranslationUri translationUri) {
        RestTemplate restTemplate = new RestTemplate();
        String remoteUrl = translationUri.getUri();
        log.info(remoteUrl);
        HashMap<String, String> response = restTemplate.getForObject(remoteUrl, new HashMap<String, String>().getClass());
        log.info(response.toString());
        return response;
    }

    public Set<String> getKeyList(Map<String, String> translation) {
        return translation.keySet();
    }

    /**
     * @param translation
     */
    @Override
    public void updateChild(Translation translation) {
        List<Translation> translations = repository.findAllByExtendedAndStatus(translation.getId(), BaseModel.STATUS.EXTENDED);
        if (translations == null) {
            return;
        }
        translations = translations.stream().peek(t -> t.update(translation)).collect(Collectors.toList());
        translations = repository.saveAll(translations);
        translations.stream().peek(this::updateChild).count();
    }

    @Override
    public void addChild(Translation translation) {
        List<Key> keys = keyService.getRepository().findAllByExtended(translation.getKeys().getId());
        List<Translation> translations = keys.stream().map(k -> new Translation(translation, k)).collect(Collectors.toList());
        translations = repository.saveAll(translations);
        translations.stream().peek(this::addChild).count();
    }
}
