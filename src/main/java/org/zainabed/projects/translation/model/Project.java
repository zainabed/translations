package org.zainabed.projects.translation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="translation_project")
public class Project extends BaseModel {

	@NotNull
	@Size(min = 5, max = 15)
	@Column(length = 15, nullable = false)
	private String name;

	@NotNull
	@Size(min = 10, max = 200)
	@Column(length = 200, nullable = false)
	private String description;

	@Column(length = 300, nullable = true)
	@Max(300)
	private String imageUri;

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

}
