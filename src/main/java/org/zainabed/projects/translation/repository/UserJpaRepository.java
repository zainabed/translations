package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.User;


//@RepositoryRestResource(path="users")
@PreAuthorize("permitAll()")
public interface UserJpaRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	public User findByUsernameAndPassword(String username, String password);

	/*@PreAuthorize("permitAll()")
	@Override
	User save(User entity);

	@PreAuthorize("permitAll()")
	@Override
	User saveAndFlush(User entity);*/
}
