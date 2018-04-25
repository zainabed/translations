package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.zainabed.projects.translation.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@RestResource(path = "user")
	public List<Project> findByUsers_Id(Long userId);
}
