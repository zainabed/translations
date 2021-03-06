package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.zainabed.projects.translation.model.UserProjectRole;

import java.util.Arrays;
import java.util.List;

//@RepositoryRestResource(path = "access", collectionResourceRel="access", excerptProjection = UserProjectRoleView.class)
public interface UserProjectRoleRepository extends JpaRepository<UserProjectRole, Long> {

    List<UserProjectRole> findByProjects_Id(Long projectId);
}
