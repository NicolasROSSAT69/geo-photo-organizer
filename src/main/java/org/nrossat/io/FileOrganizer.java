package org.nrossat.io;

import java.io.File;

public class FileOrganizer {
    public static File ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}
