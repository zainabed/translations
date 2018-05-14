package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_key")
public class Key extends BaseModel {

	@NotNull
	@Size(min = 5, max = 30)
	@Column(length = 30, nullable = false)
	private String name;

	@NotNull
	@Size(min = 5, max = 50)
	@Column(length = 50, nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "projects_id")
	private Project projects;

	@OneToMany(mappedBy="keys")
	private List<Translation> translations;
	
	
	
	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public Project getProjects() {
		return projects;
	}

	public void setProjects(Project projects) {
		this.projects = projects;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
