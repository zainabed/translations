package org.zainabed.projects.translation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_locale")
public class Locale extends BaseModel {

	@NotNull
	@Size(min = 5, max = 20)
	@Column(length = 20, nullable = false)
	private String name;

	@NotNull
	@Size(min = 5, max = 6)
	@Column(length = 6, nullable = false)
	private String code;

	@ManyToOne
	@JoinColumn(name="projectsId")
	private Project projects;

	private Boolean defualt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getDefualt() {
		return defualt;
	}

	public void setDefualt(Boolean defualt) {
		this.defualt = defualt;
	}

	public Project getProjects() {
		return projects;
	}

	public void setProjects(Project projects) {
		this.projects = projects;
	}

	

	
}
