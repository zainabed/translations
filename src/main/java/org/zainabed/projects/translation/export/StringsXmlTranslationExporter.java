package org.zainabed.projects.translation.export;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.xml.StringResource;
import org.zainabed.projects.translation.model.xml.StringResources;

/**
 * <p>
 * Implementation of {@link AbstractTranslationExporter} to implement build &
 * save methods to export {@link Translation} objects as Android String translation 
 * file format.</p>
 *
 * <p>
 * Example of export file content is something like this
 * 
 * <pre>
 *     <string name="key_name">translation_value</string>
 *     <string name="key_name">translation_value</string>
 *      ...
 * </pre>
 *
 *
 * </p>
 *
 * @author Zainul Shaikh
 */
public class StringsXmlTranslationExporter extends AbstractTranslationExporter {

	/**
	 * Android String XML representation object.
	 */
    private StringResources stringResources;
    private Logger logger = Logger.getLogger(StringsXmlTranslationExporter.class.getName());

    /**
     * Constructor sets file name prefix and extension.
     * 
     */
    public StringsXmlTranslationExporter() {
        super();
        withName = false;
        fileNamePrefix = "strings";
        fileExtension = ".xml";
        stringResources = new StringResources();
    }

    /**
     * This method transform {@link Translation} objects into {@link StringResource} Android
     * compatible translation objects.
     *
     * @param translations List of {@link Translation} object.
     */
    @Override
    public void build(List<Translation> translations) {
        List<StringResource> stringResources = translations.stream().map(StringResource::new).collect(Collectors.toList());
        this.stringResources.setStringResources(stringResources);
    }

    /**
	 * Method saves {@link StringResourcesR} object into translation XML file.
	 * 
	 * @param String File name
	 * @return Translation file URI
	 */
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
