package org.zainabed.projects.translation.importer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.zainabed.projects.translation.model.xml.ResxData;
import org.zainabed.projects.translation.model.xml.ResxRoot;

/**
 * Implementation of {@link AbstractTranslationImporter} to implement build method
 * to transform .Net frameworks translation file content into {@link Map} key value pairs which
 * then to can be imported into application.
 *
 * @author Zainul Shaikh
 */
class ResxTranslationImporter extends AbstractTranslationImporter {

	/**
	 * Method read saved Resx translation file and transform content into {@link Map} key value paris.
	 *
	 * @param fileName Absolute path of save file
	 * @return {@link Map} key value pairs
	 */
	@Override
	public Map<String, String> build(String fileName) {
		try {
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(ResxRoot.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ResxRoot resxRoot = (ResxRoot) jaxbUnmarshaller.unmarshal(file);
			List<ResxData> resxDatas = resxRoot.getResxDatas();
			
			return resxDatas.stream()
					.collect(Collectors.toMap(ResxData::getName, ResxData::getValue));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
