package org.zainabed.projects.translation.export;

import org.slf4j.spi.LoggerFactoryBinder;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.xml.TranslationElement;
import org.zainabed.projects.translation.model.xml.TranslationRoot;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResxTranslationExporter extends AbstractTranslationExporter {

    Logger logger = Logger.getLogger(ResxTranslationExporter.class.getName());

    TranslationRoot translationRoot;

    public ResxTranslationExporter() {
        super();
        fileNamePrefix = "Resources.";
        fileExtension = ".resx";
    }

    @Override
    public void build(List<Translation> translations) {
        List<TranslationElement> translationElements = translations.stream().map(TranslationElement::new).collect(Collectors.toList());
        translationRoot = new TranslationRoot();
        translationRoot.setTranslationElements(translationElements);
    }

    @Override
    public String save(String name) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TranslationRoot.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(translationRoot, new File(getFilePath(name)));
        } catch (JAXBException e) {
            logger.warning(e.getLocalizedMessage().toString());
        }
        return getFileUri();
    }
}
