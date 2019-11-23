package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zainabed.projects.translation.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
