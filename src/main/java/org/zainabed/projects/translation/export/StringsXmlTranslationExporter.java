package org.zainabed.projects.translation.export;

import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.xml.StringResource;
import org.zainabed.projects.translation.model.xml.StringResources;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StringsXmlTranslationExporter extends AbstractTranslationExporter {

    private StringResources stringResources;
    private Logger logger = Logger.getLogger(StringsXmlTranslationExporter.class.getName());

    public StringsXmlTranslationExporter() {
        super();
        withLocale = false;
        fileNamePrefix = "strings";
        fileExtension = ".xml";
        stringResources = new StringResources();
    }

    @Override
    public void build(List<Translation> translations) {
        List<StringResource> stringResources = translations.stream().map(StringResource::new).collect(Collectors.toList());
        this.stringResources.setStringResources(stringResources);
    }

    @Override
    public String save(String name) {
        try {
            JAXBContext jaxbContext =  JAXBContext.newInstance(StringResources.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(stringResources, new File(getFilePath(name)));

        }catch (JAXBException e){
            logger.warning(e.getLocalizedMessage().toString());
        }
        return getFileUri();
    }
}
