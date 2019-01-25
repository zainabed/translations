package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.KeyRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeyService {

    @Autowired
    private KeyRepository repository;

    public KeyRepository getRepository() {
        return repository;
    }

    public void extendKeysForProjects(Project project, Long extendProject) {
        List<Key> keys = repository.findAllByProjectsId(extendProject);
        List<Key> newKeys = keys.stream().map(k -> {
            Key newKey = new Key();
            newKey.setProjects(project);
            newKey.setExtended(k.getId());
            newKey.setName(k.getName());
            newKey.setDescription(k.getDescription());
            return newKey;
        }).collect(Collectors.toList());
        repository.saveAll(newKeys);
    }

    public List<String> getKeyStringList(List<Key> keys) {
        return keys.stream().map(Key::getName).collect(Collectors.toList());
    }

    public List<Key> getKeyListFromTranslations(List<Translation> translations) {
        return translations.stream().map(t -> t.getKeys()).collect(Collectors.toList());
    }

    public List<Key> createNewKeysFromList(Set<String> keys, Project project) {
        List<Key> keyList = keys.stream().map(k -> {
            Key key = new Key();
            key.setName(k);
            key.setDescription(k);
            key.setProjects(project);
            return key;
        }).collect(Collectors.toList());

        return repository.saveAll(keyList);
    }
}
