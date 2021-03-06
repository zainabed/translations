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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.zainabed.projects.translation.export.TranslationExporter;
import org.zainabed.projects.translation.export.TranslationExporterFactory;
import org.zainabed.projects.translation.importer.TranslationImporter;
import org.zainabed.projects.translation.importer.TranslationImporterFactory;
import org.zainabed.projects.translation.model.BaseModel;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.TranslationUri;
import org.zainabed.projects.translation.repository.TranslationRepository;

import javax.servlet.http.HttpServletRequest;

@Component
@Order(value = 4)
public class TranslationService implements ServiceComponent<Long>, ServiceEvent<Translation> {

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
     * @param translations
     * @param projectId
     * @param localeId
     * @return
     */
    public List<Translation> save(Map<String, String> translations, Long projectId, Long localeId) {
        Locale locale = localeService.getRepository().getOne(localeId);
        Project project = projectService.getRepository().getOne(projectId);
        List<Translation> result = null;
        logger.info(translations.toString());
        try {

            // Convert translation into key array
            Set<String> newKeyList = new HashSet<>(getKeyList(translations));

            // fetch keys exist in database
            List<Key> existingKeys = keyService.getRepository().findAllByNameInAndProjectsId(newKeyList, projectId);
            List<String> existingKeyList = keyService.getKeyNames(existingKeys);

            // Separate out new keys
            newKeyList.removeAll(existingKeyList);

            // Find all translation for existing keys
            List<Translation> existingTranslations = repository.findAllByKeysInAndProjectsIdAndLocalesId(existingKeys,
                    projectId, localeId);
            List<Key> existingTranslationKeys = keyService.getKeys(existingTranslations);
            List<String> existingTranslationKeyList = keyService.getKeyNames(existingTranslationKeys);

            existingKeyList.removeAll(existingTranslationKeyList);
            existingKeys = existingKeys.stream().filter(k -> existingKeyList.contains(k.getName()))
                    .collect(Collectors.toList());

            // Add translation for existing keys
            List<Translation> existingKeyTranslations = getTranslations(existingKeys, translations, locale);

            // Modify translation according to imported translation
            List<Translation> updatedTranslations = update(existingTranslations, translations);

            // Generate new translations
            List<Key> newKeys = keyService.saveAll(newKeyList, project);
            List<Translation> newTranslations = getTranslations(newKeys, translations, locale);

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
     * @param existingTranslations
     * @param translations
     * @return
     */
    public List<Translation> update(List<Translation> existingTranslations, Map<String, String> translations) {
        return existingTranslations.stream().peek(t -> t.setContent(translations)).collect(Collectors.toList());
    }

    /**
     * @param keys
     * @param translations
     * @param locale
     * @return
     */
    public List<Translation> getTranslations(List<Key> keys, Map<String, String> translations, Locale locale) {
        return keys.stream().map(Translation::new).peek(t -> t.update(translations, locale))
                .collect(Collectors.toList());
    }

    /**
     * @param translation
     */
    @Override
    public void updateChild(Translation translation) {
        List<Translation> translations = repository.findAllByExtendedAndStatus(translation.getId(),
                BaseModel.STATUS.EXTENDED);
        if (translations == null) {
            return;
        }
        translations = translations.stream().peek(t -> t.update(translation)).collect(Collectors.toList());
        translations = repository.saveAll(translations);
        translations.stream().peek(this::updateChild).count();
    }

    /**
     * @param translation
     */
    @Override
    public void addChild(Translation translation) {
        List<Key> keys = keyService.getRepository().findAllByExtended(translation.getKeys().getId());
        List<Translation> translations = keys.stream().map(k -> new Translation(translation, k))
                .collect(Collectors.toList());
        translations = repository.saveAll(translations);
        translations.stream().peek(this::addChild).count();
    }

    /**
     * @param childProjectId
     * @param parentProjectId
     */
    @Override
    public void extend(Long childProjectId, Long parentProjectId) {
        Project project = projectService.getRepository().getOne(childProjectId);
        List<Translation> translations = repository.findAllByProjectsId(parentProjectId);
        List<Key> keys = keyService.getRepository().findAllByProjectsId(childProjectId);
        Map<Long, Key> keyMap = keys.stream().collect(Collectors.toMap(Key::getExtended, k -> k));
        List<Translation> newTranslations = translations.stream().map(Translation::new).peek(t -> {
            t.setKeys(keyMap.get(t.getKeys().getId()));
            t.setProjects(project);
        }).collect(Collectors.toList());

        repository.saveAll(newTranslations);

    }


    /**
     * @param translation
     * @return
     */
    public Set<String> getKeyList(Map<String, String> translation) {
        return translation.keySet();
    }

    /**
     * @param request
     * @param path
     * @return
     */
    public String getHostUri(HttpServletRequest request, String path) {
        String projectPath = "/projects";
        String requestPath = request.getRequestURL().toString();
        return requestPath.substring(0, requestPath.indexOf(projectPath)) + "/" + path;
    }

    /**
     * @param projectId
     * @param localeId
     * @param type
     * @param request
     * @return
     */
    public String doExport(Long projectId, Long localeId, String type, HttpServletRequest request) {
        try {
            Locale locale = localeService.getRepository().getOne(localeId);
            List<Translation> translations = repository.findAllByLocalesIdAndProjectsId(localeId, projectId);
            TranslationExporter translationExporter = TranslationExporterFactory.get(type);
            String exportFileUri = translationExporter.export(translations, locale.getCode());
            return getHostUri(request, exportFileUri);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param projectId
     * @param localeId
     * @param type
     * @param file
     * @return
     */
    public List<Translation> doImport(Long projectId, Long localeId, String type, MultipartFile file) {
        try {
            TranslationImporter translationImporter = TranslationImporterFactory.get(type);
            Map<String, String> translations = translationImporter.imports(file);
            return save(translations, projectId, localeId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param translationUri
     * @param projectId
     * @param localeId
     * @return
     */
    @Deprecated
    public List<Translation> importTranslationFromURI(TranslationUri translationUri, Long projectId, Long localeId) {
        // Fetch translation from URI
        Map<String, String> remoteTranslations = getTranslationFromURI(translationUri);
        return save(remoteTranslations, projectId, localeId);
    }

    /**
     * @param translationUri
     * @return
     */
    @Deprecated
    public Map<String, String> getTranslationFromURI(TranslationUri translationUri) {
        RestTemplate restTemplate = new RestTemplate();
        String remoteUrl = translationUri.getUri();
        log.info(remoteUrl);
        HashMap<String, String> response = restTemplate.getForObject(remoteUrl,
                new HashMap<String, String>().getClass());
        log.info(response.toString());
        return response;
    }
}
