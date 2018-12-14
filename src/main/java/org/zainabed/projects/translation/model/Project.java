package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="translation_project")
public class Project extends BaseModel {

	@NotNull
	@Size(min = 5, max = 50)
	@Column(length = 50, nullable = false)
	private String name;

	@NotNull
	@Size(min = 10, max = 500)
	@Column(length = 500, nullable = false)
	private String description;

	private String imageUri;
	
	@ManyToMany
	private List<User> users;
	
	@ManyToMany
	@JoinTable(
			name = "project_locales", 
			joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id") , 
			inverseJoinColumns  = @JoinColumn(name = "locale_id", referencedColumnName = "id") )
	private List<Locale> locales;
	
	
	@OneToMany(mappedBy="projects")
	private List<Key> keys;
	
	@OneToMany(mappedBy="projects")
	private List<Translation> translations;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public void setLocales(List<Locale> locales) {
		this.locales = locales;
	}

	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	
	
	

}
