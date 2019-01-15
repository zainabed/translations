package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.repository.KeyRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KeyService {

    @Autowired
    KeyRepository repository;

    @Autowired
    ProjectRepository projectRepository;

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
}
