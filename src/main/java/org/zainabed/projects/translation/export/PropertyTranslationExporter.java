package org.zainabed.projects.translation.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.zainabed.projects.translation.model.Translation;

/**
 * 
 * @author zain
 *
 */
public class PropertyTranslationExporter extends AbstractTranslationExporter {

	List<Translation> translaions;
	private Path fileStorageLocation;
	String extension = ".properties";
	String filePrefix = "messages_";
	String exportFolder = null;

	public PropertyTranslationExporter() {
		exportFolder = "./export/" + UUID.randomUUID().toString();
		this.fileStorageLocation = Paths.get(exportFolder).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	/**
	 * 
	 */
	@Override
	public void build(List<Translation> translations) {
		this.translaions = translations;
	}

	/**
	 * 
	 */
	@Override
	public String save(String fileName) {
		Path exportFile = null;
		String absoluteFilePath = null;
		try {
			fileName = filePrefix + fileName + extension;
			Path targetpath = fileStorageLocation.resolve(fileName);
			absoluteFilePath = targetpath.normalize().toString();
			exportFile = Paths.get("./export").toAbsolutePath().relativize(targetpath);
			PrintWriter pw = new PrintWriter(new FileOutputStream(absoluteFilePath));
			translaions.forEach(t -> {
				pw.println(t.getKeys().getName() + "=" + t.getContent());
			});

			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = exportFile.toUri().toString();
		return path.substring(path.indexOf("export"));
	}

}
