package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.zainabed.projects.translation.model.Key;

public interface KeyRepository extends JpaRepository<Key, Long> {

	@RestResource(path = "name", rel = "keys")
	List<Key> findByNameStartsWithAndProjectsId(@Param("name") String name, @Param("projects") Long projects);

}
