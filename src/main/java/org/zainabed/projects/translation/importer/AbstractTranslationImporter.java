package org.zainabed.projects.translation.importer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zainabed.projects.translation.service.TranslationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public abstract class AbstractTranslationImporter implements FileImporter, TranslationImporter {

    Path importPath;
    String importLocation;
    Path targetPath;

    public AbstractTranslationImporter() {
        importLocation = "translations/import";
        importPath = Paths.get("./" + importLocation + "/").toAbsolutePath();
    }

    @Override
    public String save(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
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

    @Override
    public Map<String, String> imports(MultipartFile file) {
        String fileName = save(file);
        return build(fileName);
    }


}
