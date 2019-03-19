package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "translation_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends BaseModel {

	@NotNull
	@Size(min = 3, max = 20)
	private String username;

	@NotNull
	@Email
	@Size(min = 3, max = 30)
	private String email;

	@NotNull
	private String password;


	@ManyToMany
	private List<Project> projects;

	/*@ManyToMany
	@JoinTable(
			name = "user_roles", 
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") , 
			inverseJoinColumns  = @JoinColumn(name = "role_id", referencedColumnName = "id") )
	private List<Role> roles;*/

	@OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
	private List<UserProjectRole> userProjectRoles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	/*public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}*/

	public List<UserProjectRole> getUserProjectRoles() {
		return userProjectRoles;
	}

	public void setUserProjectRoles(List<UserProjectRole> userProjectRoles) {
		this.userProjectRoles = userProjectRoles;
	}

}
