package org.zainabed.projects.translation.importer;

import org.springframework.web.multipart.MultipartFile;


import java.util.Map;

public interface FileImporter {
    String save(MultipartFile file);

    Map<String, String> build(String fileName);

    
}
