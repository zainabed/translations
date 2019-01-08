package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_locale")
public class Locale extends BaseModel {

	@NotNull
	@Size(min = 2, max = 20)
	@Column(length = 20, nullable = false)
	private String name;

	@NotNull
	@Size(min = 2, max = 6)
	@Column(length = 6, nullable = false)
	private String code;

	@ManyToMany
	private List<Project> projects;

	@OneToMany(mappedBy="locales")
	private List<Translation> translations;

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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	
	
}
