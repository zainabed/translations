package org.zainabed.projects.translation.model.xml;

import org.zainabed.projects.translation.model.Translation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class StringResource {
    private String name;
    private String value;

    public StringResource() {

    }

    public StringResource(Translation translation) {
        name = translation.getKeyName();
        value = translation.getContent();
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }
}
