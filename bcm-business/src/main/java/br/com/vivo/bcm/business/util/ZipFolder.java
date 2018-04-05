package br.com.vivo.bcm.business.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;


/**
 * Zip a given folder
 * @author G0054687
 */
public class ZipFolder {
	
	public static final String FILE_EXTENSION_ZIP = ".zip";
	
	public static void main(String[] args) throws IOException {
		
		String tempDirectory = System.getProperty("java.io.tmpdir");
		
		ZipFolder.zipFolder(Paths.get(tempDirectory + "teste/name.zip"));
	}
	
	/**
	 * @param destination Full path with file name. Eg. C:/Path_TO_FILE/zipname.zip
	 * @throws IOException When not able to zip files
	 */
	public static void zipFolder(Path pathZipFile) throws IOException {

		String folderToZip = pathZipFile.getParent().toString();
		Path pathZipFolder = Paths.get(folderToZip);
		
		URI uri = URI.create("jar:file:" + pathZipFile.toUri().getPath());
		 
		Map<String, String> env = new HashMap<>();		
		env.put("create", "true");

		try (FileSystem zipFileSystem = FileSystems.newFileSystem(uri, env)) {

			final Path root = zipFileSystem.getPath("/");

			// for directories, walk the file tree
			Files.walkFileTree(pathZipFolder, new java.nio.file.SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {					
					if (!file.getFileName().toString().contains(FILE_EXTENSION_ZIP)){
						final Path dest = zipFileSystem.getPath(root.toString(), file.getFileName().toString());
						Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
					}					
					return FileVisitResult.CONTINUE;	
				}
			});
		}
	}

}