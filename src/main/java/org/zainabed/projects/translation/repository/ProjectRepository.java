package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	@Query( value = "select * from Project p left join UserProjectRole upr on p.id = upr.projects_id where upr.users_id = ?1", nativeQuery = true)
	public List<Project> getProjectsFromUsersId(@Param("id") Long userId);

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
