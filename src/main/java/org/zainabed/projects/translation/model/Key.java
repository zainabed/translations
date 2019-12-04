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
    @Size(min = 1, max = 300)
    @Column(length = 300, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(length = 500, nullable = false)
    private String description;

    private Long extended;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project projects;

    @OneToMany(mappedBy = "keys")
    private List<Translation> translations;

    public Key() {

    }

    public Key(Key key) {
        this.name = key.getName();
        this.description = key.getDescription();
        this.extended = key.getId();
        this.status = STATUS.EXTENDED;
    }

    public Key(Key key, Project project) {
        this(key);
        this.projects = project;
    }

    public Key(String key, Project project) {
        name = key;
        description = key;
        this.projects = project;
    }

    public Key(Key key, Project project, STATUS status) {
        this(key, project);
        this.status = status;
    }


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

    public Long getExtended() {
        return extended;
    }

    public void setExtended(Long extended) {
        this.extended = extended;
    }

    public void update(Key key) {
        name = key.getName();
        description = key.getDescription();
    }

}
