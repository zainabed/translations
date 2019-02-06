package org.zainabed.projects.translation.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.model.Translation;
import org.zainabed.projects.translation.service.TranslationService;

@RestController
@RequestMapping("translations")
public class TranslationController {

    @Autowired
    TranslationService translationService;

    Logger logger = LoggerFactory.getLogger(TranslationController.class);

    @PutMapping(path = "/{id}")
    public Translation put(@Valid @RequestBody Translation translation) {
        logger.info("Inside Translation controller");
        translation.setStatus(Translation.STATUS.UPDATED);
        Translation saveTranslation = translationService.getRepository().save(translation);
        translationService.updateChild(translation);
        return saveTranslation;
    }
    
    @GetMapping("/export/{tempFolder}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("tempFolder") String tempFolder, @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
    	Path filePath = Paths.get("./export/" + tempFolder + "/" + fileName);
        Resource resource = null;
		try {
			resource = new UrlResource(filePath.toUri());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
