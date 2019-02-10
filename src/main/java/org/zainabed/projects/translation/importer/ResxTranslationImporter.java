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

class ResxTranslationImporter extends AbstractTranslationImporter {

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
