package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
