package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.zainabed.projects.translation.model.Project;

@PreAuthorize("hasRole('ROLE_USER')")
public interface ProjectRepository extends JpaRepository<Project, Long> {

	@RestResource(path = "user")
	public List<Project> findByUsers_Id(Long userId);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	Project save(Project entity);

	@PreAuthorize("ROLE_ADMIN")
	void deleteById(Long aLong);

	@PreAuthorize("ROLE_ADMIN")
	void delete(Project project);

	@PreAuthorize("ROLE_ADMIN")
	void deleteAll();
}
