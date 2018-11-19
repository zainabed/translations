package org.zainabed.projects.translation.lib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserEntity;
import org.zainabed.projects.translation.repository.UserJpaRepository;

import com.zainabed.spring.security.jwt.entity.UserCredential;
import com.zainabed.spring.security.jwt.entity.UserDetail;
import com.zainabed.spring.security.jwt.service.JwtAuthenticationService;

@Service
public class UserAuthentication implements JwtAuthenticationService {

	@Autowired
	UserJpaRepository repository;

	@Override
	public UserDetail authenticate(UserCredential userCredential) throws AuthenticationException {
		UserEntity userEntity = null;
		User user = repository.findByUsernameAndPassword(userCredential.getUsername(), userCredential.getPassword());
		if (user != null) {
			userEntity = new UserEntity(user);
		}
		return userEntity;
	}

}
