package org.zainabed.projects.translation.export;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.zainabed.projects.translation.model.Translation;

/**
 * <p>This class provides a skeletal implementation of the {@link TranslationExporter} and
 * {@link FileExporter} interfaces to minimize the effort required to implement these interfaces
 * to export {@link Translation} objects into different file formats.</p>
 *
 * <p>To implement export functionality for {@link Translation} objects, programmer needs only to
 * implement two methods, build and save. Documentation to these methods is maintained inside {@link FileExporter}
 * interface.</p>
 *
 * @author Zainul Shaikh
 */
public abstract class AbstractTranslationExporter implements TranslationExporter, FileExporter<Translation> {


    /**
     * File name prefix as per file export implementation.
     * It is be attached to given file name.
     */
    protected String fileNamePrefix;

    /**
     * File extension as per file  export implementation.
     * it is attached at the end of given file name.
     */
    protected String fileExtension;

    /**
     * Location of export folder of application.
     */
    protected Path exportLocation;

    /**
     * {@link Path} of given file name club with location of export folder of application.
     */
    protected Path targetPath;

    /**
     * Boolean to indicate wither file name should be attached to its prefix & extension.
     */
    protected Boolean withName;

    /**
     * Application property represents export folder location.
     */
    @Value("{file.export.path}")
    private String exportPath;

    /**
     * Constructor perform basic setup to build export folder location and
     * Create new folder for given file name with random unique UUID name.
     * <p>
     * It throws {@link RuntimeException} if there is any problem in creating
     * new export folder.
     */
    public AbstractTranslationExporter() {
        withName = true;
        exportPath = "translations/export";
        String folderPath = "./" + exportPath + "/" + UUID.randomUUID().toString();
        exportLocation = Paths.get(folderPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(exportLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    /**
     * Getter method to return prefix name of export file.
     *
     * @return String value of prefix
     */
    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    /**
     * Setter method to set prefix value of export file.
     *
     * @param fileNamePrefix prefix value as string
     */
    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    /**
     * Getter method to return file extension of export file.
     *
     * @return String value of file extension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * Setter method to set file extension value.
     *
     * @param fileExtension file extension value as string
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Implementation of {@link TranslationExporter} interface to provide generic functionality to
     * export {@link Translation} objects.
     * This method invoke abstract methods of {@link FileExporter} interface forcing concrete implementation
     * of this abstract class to define functionality of build & save methods as per their export logic.
     *
     * @param translations List of {@link Translation} objects
     * @param fileName     Basic file name
     * @return File name as URI
     */
    @Override
    public final String export(List<Translation> translations, String fileName) {
        build(translations);
        return save(fileName);
    }

    /**
     * This method modifies given file name by concatenating it file prefix and extension.
     * It returns only concatenation of prefix and extension if boolean value of withName is false.
     *
     * @param name Basic file name
     * @return Modified file name
     */
    @Override
    public final String getFileName(String name) {
        if (withName) {
            return getFileNamePrefix() + name + getFileExtension();
        }
        return getFileNamePrefix() + getFileExtension();
    }


    /**
     * This method returns absolute system file path for given string name value.
     * It generates class targetPath variable by resolving file name with export location.
     *
     * @param name File name
     * @return Normalized file path
     */
    @Override
    public String getFilePath(String name) {
        String fileName = getFileName(name);
        targetPath = exportLocation.resolve(fileName);
        return targetPath.normalize().toString();
    }

    /**
     * This method return export file path as URI.
     *
     * @return File path URI
     */
    @Override
    public String getFileUri() {
        String path = targetPath.toUri().toString();
        return path.substring(path.indexOf(exportPath));
    }

}
