package org.zainabed.projects.translation.export;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.model.xml.ResxData;
import org.zainabed.projects.translation.model.xml.ResxRoot;

/**
 * <p>
 * Implementation of {@link AbstractTranslationExporter} to implement build &
 * save methods to export {@link Translation} objects as .Net translation file
 * in Resx format.
 * </p>
 *
 * <p>
 * Example of export file content is something like this
 * 
 * <pre>
 *      <data name="key_name">
 *      	<value>translation_value</value>
 *      </data>
 *      <data name="key_name">
 *      	<value>translation_value</value>
 *      </data>
 *      ...
 * </pre>
 *
 *
 * </p>
 *
 * @author Zainul Shaikh
 */
public class ResxTranslationExporter extends AbstractTranslationExporter {

	Logger logger = Logger.getLogger(ResxTranslationExporter.class.getName());

	/**
	 * .Net Resx xml root element.
	 */
	ResxRoot resxRoot;

	/**
	 * Constructor defines file prefix and extension for Property export file.
	 */
	public ResxTranslationExporter() {
		super();
		fileNamePrefix = "Resources.";
		fileExtension = ".resx";
	}

	/**
     * This method transform {@link Translation} objects into {@link ResxData} .Net
     * compatible translation objects.
     *
     * @param translations List of {@link Translation} object.
     */
	@Override
	public void build(List<Translation> translations) {
		List<ResxData> resxDatas = translations.stream().map(ResxData::new).collect(Collectors.toList());
		resxRoot = new ResxRoot();
		resxRoot.setResxDatas(resxDatas);
	}

	
	/**
	 * Method saves {@link ResxRoot} object into translation XML file.
	 * 
	 * @param String File name
	 * @return Translation file URI
	 */
	@Override
	public String save(String name) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ResxRoot.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(resxRoot, new File(getFilePath(name)));
		} catch (JAXBException e) {
			logger.warning(e.getLocalizedMessage().toString());
		}
		return getFileUri();
	}
}
