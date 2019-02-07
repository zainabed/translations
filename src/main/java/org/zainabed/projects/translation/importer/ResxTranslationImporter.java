package org.zainabed.projects.translation.importer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.zainabed.projects.translation.model.xml.TranslationElement;
import org.zainabed.projects.translation.model.xml.TranslationRoot;

class ResxTranslationImporter extends AbstractTranslationImporter {

	@Override
	public Map<String, String> build(String fileName) {
		try {
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(TranslationRoot.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			TranslationRoot translationRoot = (TranslationRoot) jaxbUnmarshaller.unmarshal(file);
			List<TranslationElement> translationElements = translationRoot.getTranslationElements();
			
			return translationElements.stream()
					.collect(Collectors.toMap(TranslationElement::getName, TranslationElement::getValue));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
