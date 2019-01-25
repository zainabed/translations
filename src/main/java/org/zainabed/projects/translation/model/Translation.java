package org.zainabed.projects.translation.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.zainabed.projects.translation.model.event.TranslationEvent;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "translation_translation")
public class Translation extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "locales_id")
    private Locale locales;

    @ManyToOne
    @JoinColumn(name = "keys_id")
    private Key keys;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project projects;

    @NotNull
    @Size(min = 1, max = 2500)
    private String content;
    private Boolean verified;
    private Long extended;


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

    public Project getProjects() {
        return projects;
    }

    public void setProjects(Project projects) {
        this.projects = projects;
    }

    public Locale getLocales() {
        return locales;
    }

    public void setLocales(Locale locales) {
        this.locales = locales;
    }

    public Key getKeys() {
        return keys;
    }

    public void setKeys(Key keys) {
        this.keys = keys;
    }

    public static Translation factoryObject(Translation translation) {
        Translation newTranslation = new Translation();
        newTranslation.setContent(translation.getContent());
        newTranslation.setLocales(translation.getLocales());
        return newTranslation;
    }

    public Long getExtended() {
        return extended;
    }

    public void setExtended(Long extended) {
        this.extended = extended;
    }

    public void update(Translation translation){
        content = translation.getContent();
    }
}
