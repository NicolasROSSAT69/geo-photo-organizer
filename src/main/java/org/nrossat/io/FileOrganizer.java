package org.nrossat.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileOrganizer {
    public static File ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public static void copyFileToDirectory(File inputFile, File destinationDirectoryPath){
        File destinationFile = new File(destinationDirectoryPath, inputFile.getName());
        try {
            Files.copy(inputFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
