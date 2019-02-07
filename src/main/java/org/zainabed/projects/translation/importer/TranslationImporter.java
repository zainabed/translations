package org.zainabed.projects.translation.importer;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TranslationImporter {
    Map<String, String> imports(MultipartFile file);

}
