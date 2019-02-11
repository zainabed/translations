package org.zainabed.projects.translation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.service.TranslationService;
import org.zainabed.projects.translation.model.Translation;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Rest controller for {@link Translation} entity model.
 * It is design to provide additional Rest APIs which could not be produced from common
 * Spring Data JPA Rest services for {@link Translation} model.
 *
 * @author Zainul Shaikh
 */
@RestController
@RequestMapping("translations")
public class TranslationController {

    @Autowired
    TranslationService translationService;

    Logger logger = LoggerFactory.getLogger(TranslationController.class);

    /**
     * Method create exported translation file {@link Resource} and send {@link ResponseEntity} as
     * downloadable file to client.
     *
     * @param tempFolder Temporary folder produced during project export method.
     * @param fileName   File to be exported
     * @param request    {@link HttpServletRequest} object
     * @return {@link ResponseEntity} to download file
     */
    @GetMapping("/export/{tempFolder}/{fileName:.+}")
    public ResponseEntity<Resource> export(@PathVariable("tempFolder") String tempFolder,
                                           @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Path filePath = Paths.get("./translations/export/" + tempFolder + "/" + fileName);
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
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
