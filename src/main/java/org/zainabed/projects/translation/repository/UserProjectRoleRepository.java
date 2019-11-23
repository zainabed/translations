package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.zainabed.projects.translation.model.UserProjectRole;
import org.zainabed.projects.translation.model.projection.UserProjectRoleView;

@RepositoryRestResource(path = "access", collectionResourceRel="access")
public interface UserProjectRoleRepository extends JpaRepository<UserProjectRole, Long> {

}
