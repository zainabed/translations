package org.zainabed.projects.translation.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zainabed.projects.translation.model.Translation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Implementation of {@link AbstractTranslationExporter} to implement build & save
 * methods to export {@link Translation} objects as JSON translation file.</p>
 *
 * <p>Example of export file content is something like this
 * <pre>
 *      {
 *          "translation_key" : "translation_value",
 *          "translation_key" : "translation_value",
 *          "translation_key" : "translation_value"
 *      }
 *  </pre>
 * To generate JSON response class transforms {@link Translation} object into key value pair
 * map and save it in class field.
 * </p>
 *
 * @author Zainul Shaikh
 */
public class JsonTranslationExporter extends AbstractTranslationExporter {

    /**
     * Key value pair of translations generated from list of {@link Translation}.
     */
    private Map<String, String> translations;

    /**
     * Constructor define file prefix and extension for JSON export file.
     */
    JsonTranslationExporter() {
        super();
        fileNamePrefix = "";
        fileExtension = ".json";
    }

    /**
     * Method transform given {@link Translation} objects into key value pair map of
     * translation.
     *
     * @param models List of {@link Translation} object.
     */
    @Override
    public void build(List<Translation> models) {
        translations = models.stream().collect(Collectors.toMap(Translation::getKeyName, Translation::getContent));
    }

    /**
     * Method persist transformed translation map into JSON file and return
     * file URI.
     *
     * @param name File name
     * @return Saved file URI
     */
    @Override
    public String save(String name) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(getFilePath(name)), translations);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getFileUri();
    }
}
