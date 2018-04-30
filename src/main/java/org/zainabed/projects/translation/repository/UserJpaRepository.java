package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.User;


@RepositoryRestResource(path="users")
public interface UserJpaRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
