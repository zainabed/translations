package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.KeyRepository;
import org.zainabed.projects.translation.repository.ProjectRepository;
import org.zainabed.projects.translation.repository.TranslationRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class TranslationService {

    @Autowired
    TranslationRepository repository;

    @Autowired
    KeyRepository keyRepository;

    @Autowired
    ProjectRepository projectRepository;

    public void extendTranslationsFor(Project project, Long extendProjectId) {

        List<Translation> translations = repository.findAllByProjectsId(extendProjectId);
        List<Key> keys = keyRepository.findAllByProjectsId(project.getId());
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
}
