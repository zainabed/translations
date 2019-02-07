package org.zainabed.projects.translation.export;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.zainabed.projects.translation.model.Translation;

/**
 * @author zain
 */
public abstract class AbstractTranslationExporter implements TranslationExporter, FileExporter<Translation> {


    protected String fileNamePrefix;
    protected String fileExtension;
    protected Path exportLocation;
    protected Path targetPath;
    protected Boolean withLocale;

    @Value("{file.export.path}")
    private String exportPath;

    public AbstractTranslationExporter() {
        withLocale = true;
        exportPath = "translations/export";
        String folderPath = "./" + exportPath + "/" + UUID.randomUUID().toString();
        exportLocation = Paths.get(folderPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(exportLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * @param models
     * @return
     */
    @Override
    public final String export(List<Translation> translations, String fileName) {
        build(translations);
        return save(fileName);
    }

    @Override
    public final String getFileName(String name) {
        if (withLocale) {
            return getFileNamePrefix() + name + getFileExtension();
        }
        return getFileNamePrefix() + getFileExtension();
    }


    @Override
    public String getFilePath(String name) {
        String fileName = getFileName(name);
        targetPath = exportLocation.resolve(fileName);
        return targetPath.normalize().toString();
    }

    @Override
    public String getFileUri() {
        String path = targetPath.toUri().toString();
        return path.substring(path.indexOf(exportPath));
    }

}
