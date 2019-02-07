package org.zainabed.projects.translation.importer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyTranslationImporter extends AbstractTranslationImporter {

	@Override
	public Map<String, String> build(String fileName) {
		try {
			Map<String, String> propertyMap = new HashMap<>();
			FileReader fileReader = new FileReader(fileName);
			Properties properties = new Properties();
			properties.load(fileReader);

			Enumeration keys = properties.propertyNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				propertyMap.put(key, properties.getProperty(key));
			}
			fileReader.close();
			return propertyMap;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
