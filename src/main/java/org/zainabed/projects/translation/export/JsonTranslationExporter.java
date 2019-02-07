package org.zainabed.projects.translation.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zainabed.projects.translation.model.MapValue;
import org.zainabed.projects.translation.model.Translation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonTranslationExporter extends AbstractTranslationExporter {

    private Map<String, String> translations;

    JsonTranslationExporter() {
        super();
        fileNamePrefix = "";
        fileExtension = ".json";
    }

    @Override
    public void build(List<Translation> models) {
        translations = models.stream().collect(Collectors.toMap(Translation::getKeyName, Translation::getContent));
    }

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
