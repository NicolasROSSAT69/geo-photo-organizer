package org.nrossat.processing;

import com.drew.metadata.exif.ExifIFD0Directory;
import org.nrossat.io.FileOrganizer;
import org.nrossat.ui.ConsoleDisplay;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageProcessor {
    public static void organizeByDate(File[] inputFiles, String outputDirectoryPath) {
        AtomicInteger nbrImageOk = new AtomicInteger(0);
        AtomicInteger nbrImageNok = new AtomicInteger(0);

        Arrays.stream(inputFiles).parallel().forEach(inputFile -> {
            try {
                ExifIFD0Directory exifIFD0Directory = ImageMetadataExtractor.extractMetadata(inputFile);
                if (exifIFD0Directory != null) {
                    String dateTime = ImageDateParser.parseDate(exifIFD0Directory);
                    File destinationFile;
                    if (dateTime != null) {
                        File folderDate = FileOrganizer.ensureDirectoryExists(outputDirectoryPath + "/" + dateTime);
                        destinationFile = new File(folderDate, inputFile.getName());
                        nbrImageOk.incrementAndGet();
                    } else {
                        File folderIgnore = FileOrganizer.ensureDirectoryExists(outputDirectoryPath + "/Photos_Autres");
                        destinationFile = new File(folderIgnore, inputFile.getName());
                        nbrImageNok.incrementAndGet();
                    }
                    Files.copy(inputFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ConsoleDisplay.displayMessage(nbrImageOk.get() + " images triées");
        ConsoleDisplay.displayMessage(nbrImageNok.get() + " images non triées, elles sont rangées dans le dossier Photos_Autres");
    }
}
