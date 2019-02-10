package org.zainabed.projects.translation.model.xml;


import org.zainabed.projects.translation.model.Translation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "data")
public class ResxData {

    private String value;
    private String name;

    public ResxData() {

    }

    public ResxData(Translation translation) {
        name = translation.getKeyName();
        value = translation.getContent();
    }

    public String getValue() {
        return value;
    }

    @XmlElement
    public void setValue(String value) {
        this.value = value;
    }


    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }
}
