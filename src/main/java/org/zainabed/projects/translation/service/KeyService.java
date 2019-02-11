package org.zainabed.projects.translation.service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.BaseModel;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.repository.KeyRepository;

@Component
@Order(value=3)
public class KeyService implements ServiceComponent<Long>, ServiceEvent<Key> {

	@Autowired
	ProjectService projectService;

	@Autowired
	private KeyRepository repository;

	Logger log = Logger.getLogger(KeyService.class.getName());

	/**
	 * 
	 * @return
	 */
	public KeyRepository getRepository() {
		return repository;
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> getKeyNames(List<Key> keys) {
		return keys.stream().map(Key::getName).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param translations
	 * @return
	 */
	public List<Key> getKeys(List<Translation> translations) {
		return translations.stream().map(Translation::getKeys).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param keys
	 * @param project
	 * @return
	 */
	public List<Key> saveAll(Set<String> keys, Project project) {
		List<Key> keyList = keys.stream().map(getKey(project)).collect(Collectors.toList());
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
		List<Project> projects = projectService.getRepository().findAllByExtended(key.getProjects().getId());
		List<Key> keys = projects.stream().map(p -> new Key(key, p)).collect(Collectors.toList());
		keys = repository.saveAll(keys);
		keys.stream().peek(this::addChild).count();
	}

	@Override
	public void extend(Long childProjectId, Long parentProjectId) {
		Project project = projectService.getRepository().getOne(childProjectId);
		List<Key> keys = repository.findAllByProjectsId(parentProjectId);
		List<Key> newKeys = keys.stream().map(getKey(project, BaseModel.STATUS.EXTENDED)).collect(Collectors.toList());
		repository.saveAll(newKeys);

	}

	public static Function<String, Key> getKey(Project p) {
		return s -> {
			return new Key(s, p);
		};
	}

	public static Function<Key, Key> getKey(Project p, BaseModel.STATUS s) {
		return k -> {
			return new Key(k, p, s);
		};
	}
}
