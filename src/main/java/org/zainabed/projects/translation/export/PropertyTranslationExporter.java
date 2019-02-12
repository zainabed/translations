package org.zainabed.projects.translation.export;

import org.zainabed.projects.translation.model.Translation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * <p>Implementation of {@link AbstractTranslationExporter} to implement build & save
 * methods to export {@link Translation} objects as Java Property file.</p>
 *
 * <p>Example of export file content is something like this
 * <pre>
 *      translation_key = translation_value
 *      translation_key = translation_value
 *      translation_key = translation_value
 *  </pre>
 *
 *
 * </p>
 *
 * @author Zainul Shaikh
 */
public class PropertyTranslationExporter extends AbstractTranslationExporter {

    /**
     * {@link Translation} objects to exported into Property file.
     */
    private List<Translation> translaions;
    private PrintWriter pw;

    /**
     * Constructor define file prefix and extension for Property export file.
     */
    public PropertyTranslationExporter() {
        super();
        this.fileNamePrefix = "messages_";
        this.fileExtension = ".properties";
    }

    /**
     * This method act as setter for translations field as per contract of {@link AbstractTranslationExporter}.
     *
     * @param translations List of {@link Translation} object.
     */
    @Override
    public void build(List<Translation> translations) {
        this.translaions = translations;
    }

    /**
     * Method generates Property file using provided translations and saved into
     * given file name.
     *
     * @param name File name
     * @return Export file URI
     */
    @Override
    public String save(String name) {
        try {
            pw = new PrintWriter(new FileOutputStream(getFilePath(name)));
            translaions.forEach(this::println);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getFileUri();
    }

    /**
     * Generate property value from given {@link Translation} object.
     *
     * @param translation {@link Translation} object
     */
    protected void println(Translation translation) {
        pw.println(translation.getKeys().getName() + "=" + translation.getContent());
    }

}
