package org.zainabed.projects.translation.importer;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>A file importer interface. implementation of this interface will provide generic functionality to
 * import {@link MultipartFile} file of given type.</p>
 *
 * <p>This interface provides two methods to save file and produce {@link Map} of key value pairs.
 * Interface allows user to have freedom to decide model structure to import file content which depend on
 * implementation class.
 * </p>
 *
 * <p>Concept of import is to save given file into system and return {@link Map} key value pairs to client to process
 * it as per there implementation.</p>
 *
 * @author Zainul Shaikh
 */
public interface FileImporter {

    /**
     * Method should save given {@link MultipartFile} file into system.
     * and save absolute file name inside implementation for later use.
     *
     * @param file {@link MultipartFile} file
     * @return Absolute file name
     */
    String save(MultipartFile file);

    /**
     * Method should process the save file content and produce {@link Map} key value paris
     * to be used inside application as per the their need.
     *
     * @param fileName Absolute path of save file
     * @return {@link Map} key value pair of file content.
     */
    Map<String, String> build(String fileName);

}
