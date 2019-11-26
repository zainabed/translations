package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.zainabed.projects.translation.model.Role;
import org.zainabed.projects.translation.model.projection.RoleView;

@RepositoryRestResource(excerptProjection = RoleView.class)
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
