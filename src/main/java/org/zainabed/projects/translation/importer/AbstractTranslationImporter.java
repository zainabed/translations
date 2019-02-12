package org.zainabed.projects.translation.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>This class provides a skeletal implementation of the {@link TranslationImporter} and
 * {@link FileImporter} interfaces to minimize the effort required to implement these interfaces
 * to import {@link Map} key value paris from different file formats.</p>
 *
 * <p>To implement import functionality programmer needs only to implement two methods, build and save.
 * Documentation to these methods is maintained inside {@link FileImporter} interface.</p>
 *
 * @author Zainul Shaikh
 */
public abstract class AbstractTranslationImporter implements FileImporter, TranslationImporter {

    /**
     * Imported files location relative to system class path.
     */
    Path importPath;

    /**
     * Absolute path of given file name.
     */
    Path targetPath;

    /**
     * Application property for import file location
     */
    String importLocation;

    /**
     * Constructor method initiates import directory location and instantiates import {@link Path} object.
     * iT throws an exception when it is unable to create import directory.
     */
    public AbstractTranslationImporter() {
        importLocation = "translations/import";
        String folderPath = "./" + importLocation + "/" + UUID.randomUUID().toString();
        importPath = Paths.get(folderPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(importPath);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * This method saves given {@link MultipartFile} file into system at target path.
     * It throws exceptions if some error occurs while saving the file.
     * It then return absolute path of saved file.
     *
     * @param file {@link MultipartFile} File to import
     * @return Absolute path of saved file
     */
    @Override
    public String save(MultipartFile file) {
        // Normalize file name
        String fileName = null;

        try {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            targetPath = this.importPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return targetPath.normalize().toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * Implementation of {@link TranslationImporter} interface to provide generic functionality to
     * import {@link MultipartFile} file.
     * <p>
     * This method saves {@link MultipartFile} file and invoke build method to convert file content into
     * {@link Map} key value pairs.
     * <p>
     * Build method here is abstraction whose intended purpose dependence on implementation class.
     *
     * @param file Import file name
     * @return {@link Map} key value pairs
     */
    @Override
    public Map<String, String> imports(MultipartFile file) {
        String fileName = save(file);
        return build(fileName);
    }

}
