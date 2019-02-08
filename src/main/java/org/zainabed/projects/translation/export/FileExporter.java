package org.zainabed.projects.translation.export;

import java.util.List;

/**
 * <p>A file exporter interface. implementation of this interface will provide generic functionality to
 * export entity model of given type.</p>
 *
 * <p>This interface provides three methods to retrieve file name according context of method.
 * Interface allows user to have freed to decide model structure to export and it depend on
 * implementation class.
 * </p>
 *
 * <p>Concept of export is to save given data structure into file system and return file URI to client to download
 * it as per there implementation.</p>
 *
 * @param <T> Entity model
 * @author Zainul Shaikh
 */
public interface FileExporter<T> {

    /**
     * Build given entity model into user specific data structure.
     * Implementation should persist this data structure into class variable
     * to use it in other methods of this interface.
     *
     * @param models List of entity model
     */
    void build(List<T> models);

    /**
     * Method save generated data structure from given entity model list into
     * a file system whose name and conventions are decided from other file name
     * methods this interface.
     * Method should return absolute path of saved file.
     *
     * @param name File name
     * @return Absolute file path
     */
    String save(String name);

    /**
     * Method should build file name according requirement of implementation
     * and return generated file name.
     *
     * @param name Basic file name
     * @return Implementation specific file name
     */
    String getFileName(String name);

    /**
     * Method should return absolute system file path of generated file name.
     * Instead of return {@link java.nio.file.Path} method should return resolved name.
     *
     * @param name File name
     * @return Resolved system file path
     */
    String getFilePath(String name);

    /**
     * Method should return file path in URI format to enable it to attached itself with
     * application host name to generate downloadable file URI
     *
     * @return File name as URI
     */
    String getFileUri();
}
