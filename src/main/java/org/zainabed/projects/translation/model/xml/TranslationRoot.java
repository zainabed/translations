package org.zainabed.projects.translation.model.xml;

import org.zainabed.projects.translation.model.Translation;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "root")
@XmlType(name = "")
@XmlAccessorType(XmlAccessType.FIELD)
public class TranslationRoot {

    @XmlElement(name = "data")
    public List<TranslationElement> translationElements;

    public List<TranslationElement> getTranslationElements() {
        return translationElements;
    }

    public void setTranslationElements(List<TranslationElement> translationElements) {
        this.translationElements = translationElements;
    }
}
