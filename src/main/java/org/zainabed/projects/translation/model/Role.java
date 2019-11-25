package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	//@ManyToMany
	//private List<User> users;

	@OneToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	private List<UserProjectRole> access;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}*/

	public List<UserProjectRole> getAccess() {
		return access;
	}

	public void setAccess(List<UserProjectRole> userProjectRoles) {
		this.access = userProjectRoles;
	}
}
