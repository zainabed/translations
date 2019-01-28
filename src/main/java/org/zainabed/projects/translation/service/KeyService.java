package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.BaseModel;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.KeyRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class KeyService implements ModelService<Key> {

    @Autowired
    ProjectService projectService;
    Logger log = Logger.getLogger(KeyService.class.getName());

    public static Function<String, Key> getKeyFromProject(Project p) {
        return s -> {
            return new Key(s, p);
        };
    }

    public static Function<Key, Key> getKeyFromProjectAndStatus(Project p, BaseModel.STATUS s) {
        return k -> {
            return new Key(k, p, s);
        };
    }

    @Autowired
    private KeyRepository repository;

    public KeyRepository getRepository() {
        return repository;
    }

    public void extendKeysForProjects(Project project, Long extendProject) {
        List<Key> keys = repository.findAllByProjectsId(extendProject);
        List<Key> newKeys = keys.stream().map(getKeyFromProjectAndStatus(project, BaseModel.STATUS.EXTENDED)).collect(Collectors.toList());
        repository.saveAll(newKeys);
    }

    public List<String> getKeyStringList(List<Key> keys) {
        return keys.stream().map(Key::getName).collect(Collectors.toList());
    }

    public List<Key> getKeyListFromTranslations(List<Translation> translations) {
        return translations.stream().map(Translation::getKeys).collect(Collectors.toList());
    }

    public List<Key> createNewKeysFromList(Set<String> keys, Project project) {
        List<Key> keyList = keys.stream().map(getKeyFromProject(project)).collect(Collectors.toList());
        return repository.saveAll(keyList);
    }


    @Override
    public void updateChild(Key key) {
        List<Key> keys = repository.findAllByExtendedAndStatus(key.getId(), BaseModel.STATUS.EXTENDED);
        if (keys == null) {
            return;
        }
        keys = keys.stream().peek(k -> k.update(key)).collect(Collectors.toList());
        repository.saveAll(keys);
        keys.stream().peek(this::updateChild).count();
    }

    @Override
    public void addChild(Key key) {
        log.info( "***********" + key.getId() + "***********");

        List<Project> projects = projectService.getRepository().findAllByExtended(key.getProjects().getId());
        List<Key> keys = projects.stream().map(p -> new Key(key, p)).collect(Collectors.toList());
        keys = repository.saveAll(keys);
        keys.stream().peek(this::addChild).count();
    }
}
