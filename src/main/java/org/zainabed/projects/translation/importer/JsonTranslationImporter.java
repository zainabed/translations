package org.zainabed.projects.translation.importer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of {@link AbstractTranslationImporter} to implement build method
 * to transform JSON translation file content into {@link Map} key value pairs which
 * then to can be imported into application.
 *
 * @author Zainul Shaikh
 */
public class JsonTranslationImporter extends AbstractTranslationImporter {

    /**
     * Method read saved JSON translation file and transform content into {@link Map} key value paris.
     *
     * @param fileName Absolute path of save file
     * @return {@link Map} key value pairs
     */
    @Override
    public Map<String, String> build(String fileName) {
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        File file = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, typeRef);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
