package org.zainabed.projects.translation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.zainabed.spring.security.jwt.entity.UserDetail;

import io.jsonwebtoken.Claims;

public class UserEntity implements UserDetail {

	private String username;
	private String password;
	private List<String> roles;

	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserEntity(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		List<Role> roles = user.getRoles();
		if (roles != null) {
			this.roles = roles.stream().map(role -> role.getName()).collect(Collectors.toList());
		} else {
			this.roles = new ArrayList<>();
			this.roles.add("ROLE_USER");
		}

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public List<String> getRoles() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void build(Claims climas) {
		// TODO Auto-generated method stub

	}

}
