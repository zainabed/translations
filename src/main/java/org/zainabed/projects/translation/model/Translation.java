package org.zainabed.projects.translation.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(max = 2500)
    private String content;
    private Boolean verified;
    private Long extended;


    public Translation(){

    }

    public Translation(Translation translation){
        content = translation.getContent();
        locales = translation.getLocales();
        extended = translation.getId();
        keys = translation.getKeys();
        status = STATUS.EXTENDED;
    }

    public Translation(Key keys){
        projects = keys.getProjects();
        this.keys = keys;
    }
    
    public Translation(Translation translation, Key keys){
        this(translation);
		projects = keys.getProjects();
		this.keys = keys;
    }
    
   

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setContent(Map<String, String> translations) {
        this.content = translations.get(getKeyName());
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

    public String getKeyName(){
        return getKeys().getName();
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

	public void update(Map<String, String> translations, Locale locales) {
		content = translations.get(getKeyName());
		this.locales = locales;
	}
}
