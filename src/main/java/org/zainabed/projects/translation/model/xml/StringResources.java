package org.zainabed.projects.translation.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "resources")
@XmlAccessorType(XmlAccessType.FIELD)
public class StringResources {

    @XmlElement(name = "string")
    private List<StringResource> stringResources;

    public List<StringResource> getStringResources() {
        return stringResources;
    }

    public void setStringResources(List<StringResource> stringResources) {
        this.stringResources = stringResources;
    }
}
