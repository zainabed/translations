package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_translation")
public class Translation extends BaseModel {

	@NotNull
	@OneToMany(mappedBy = "translations")
	private List<Locale> locales;

	@OneToMany(mappedBy = "translations")
	private List<Key> keys;

	@ManyToOne
	@JoinColumn(name = "projectsId")
	private Project projects;

	@NotNull
	private String content;
	private Boolean verified;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
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

	public Project getProjects() {
		return projects;
	}

	public void setProjects(Project projects) {
		this.projects = projects;
	}

	
}
