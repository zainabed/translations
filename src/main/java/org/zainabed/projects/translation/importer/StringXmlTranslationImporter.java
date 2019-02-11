package org.zainabed.projects.translation.importer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.zainabed.projects.translation.model.xml.StringResource;
import org.zainabed.projects.translation.model.xml.StringResources;

/**
 * Implementation of {@link AbstractTranslationImporter} to implement build method
 * to transform Strings XML translation file content into {@link Map} key value pairs which
 * then to can be imported into application.
 *
 * @author Zainul Shaikh
 */
public class StringXmlTranslationImporter extends AbstractTranslationImporter {

    /**
     * Method read saved String xml translation file and transform content into {@link Map} key value paris.
     *
     * @param fileName Absolute path of save file
     * @return {@link Map} key value pairs
     */
    @Override
    public Map<String, String> build(String fileName) {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(StringResources.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringResources stringResources = (StringResources) jaxbUnmarshaller.unmarshal(file);
            List<StringResource> stringResourceList = stringResources.getStringResources();
            return stringResourceList.stream()
                    .collect(Collectors.toMap(StringResource::getName, StringResource::getValue));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
