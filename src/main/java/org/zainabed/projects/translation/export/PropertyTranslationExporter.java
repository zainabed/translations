package org.zainabed.projects.translation.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.zainabed.projects.translation.model.Translation;

/**
 * @author zain
 */
public class PropertyTranslationExporter extends AbstractTranslationExporter {

    List<Translation> translaions;

    String exportFolder = null;

    public PropertyTranslationExporter() {
        super();
        this.fileNamePrefix = "messages_";
        this.fileExtension = ".properties";
    }

    /**
     *
     */
    @Override
    public void build(List<Translation> translations) {
        this.translaions = translations;
    }

    /**
     *
     */
    @Override
    public String save(String name) {
        String filePath = null;
        try {
            filePath = getFilePath(name);
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
            translaions.forEach(t -> {
                pw.println(t.getKeys().getName() + "=" + t.getContent());
            });

            pw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getFileUri();
    }

}
