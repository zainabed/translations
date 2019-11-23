package org.zainabed.projects.translation.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_project")
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

    private Long extended;

    //@ManyToMany
    //private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_locales",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "locale_id", referencedColumnName = "id"))
    private Set<Locale> locales;


    @OneToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private List<Key> keys;


    @OneToMany(mappedBy = "projects",fetch = FetchType.LAZY)
    private List<Translation> translations;

    @OneToMany(mappedBy = "projects",fetch = FetchType.LAZY)
    private List<UserProjectRole> access;


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

    /*public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    public Set<Locale> getLocales() {
        return locales;
    }

    public void setLocales(Set<Locale> locales) {
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


    public Long getExtended() {
        return extended;
    }

    public void setExtended(Long extended) {
        this.extended = extended;
    }

    public List<UserProjectRole> getAccess() {
        return access;
    }

    public void setAccess(List<UserProjectRole> userProjectRoles) {
        this.access = userProjectRoles;
    }
}
