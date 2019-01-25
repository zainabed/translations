package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.zainabed.projects.translation.model.Project;
import org.zainabed.projects.translation.model.projection.ProjectView;


@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource(excerptProjection = ProjectView.class)
public interface ProjectRepository extends JpaRepository<Project, Long> {

	@RestResource(path = "user")
	public List<Project> findByUsers_Id(Long userId);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	Project save(Project entity);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	void deleteById(Long aLong);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	void delete(Project project);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	void deleteAll();

    List<Project> findAllByExtended(@Param("extended") Long extended);
}
