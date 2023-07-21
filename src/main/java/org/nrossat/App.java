package org.nrossat;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicInteger;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {

        displayBanner();
        String inputDirectoryPath = getInputPath("Veuillez saisir le chemin du repertoire des images :");
        String outputDirectoryPath = getInputPath("Veuillez saisir le chemin du repertoire de sortie :");

        processImages(inputDirectoryPath, outputDirectoryPath);

    }

    private static void displayBanner() {
        System.out.println("###############################");
        System.out.println("#                             #");
        System.out.println("#         Application         #");
        System.out.println("#       GeoPhotoOrganizer     #");
        System.out.println("#                             #");
        System.out.println("#  Triez vos photos par date  #");
        System.out.println("#  et copiez-les dans le      #");
        System.out.println("#  dossier correspondant.     #");
        System.out.println("#                             #");
        System.out.println("###############################");
        System.out.println();
    }

    private static String getInputPath(String message) {
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

    private static File[] getInputFiles(File inputDirectory) {
        return inputDirectory.listFiles((dir, name) ->
                name.toLowerCase().matches(".*\\.(jpg|png|jpeg|heic)$")
        );
    }

    private static void processImages(String inputDirectoryPath, String outputDirectoryPath) {
        File inputDirectory = new File(inputDirectoryPath);
        File[] inputFiles = getInputFiles(inputDirectory);

        if (inputFiles == null || inputFiles.length == 0) {
            System.out.println("Aucun fichier image trouvé dans le dossier d'entrée.");
            return;
        }

        File outputDirectory = ensureDirectoryExists(outputDirectoryPath);
        processImageFiles(inputFiles, inputDirectoryPath, outputDirectoryPath);
    }

    private static File ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private static void processImageFiles(File[] inputFiles, String inputDirectoryPath, String outputDirectoryPath) {
        AtomicInteger nbrImageOk = new AtomicInteger(0);
        AtomicInteger nbrImageNok = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        Arrays.stream(inputFiles).parallel().forEach(inputFile -> {
            File imageFile = new File(inputDirectoryPath+"/"+inputFile.getName());

            try {
                Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

                // Récupérer et afficher la date/heure
                ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

                if (exifIFD0Directory != null) {

                    String dateTime = exifIFD0Directory.getString(ExifIFD0Directory.TAG_DATETIME);

                    if (dateTime != null){
                        dateTime = dateTime.split(" ")[0];
                        dateTime = dateTime.replace(':', '-');
                        String folderDateDir = outputDirectoryPath + "/" + dateTime;
                        File folderDate = new File(folderDateDir);

                        if (!folderDate.exists()){
                            folderDate.mkdirs();
                        }

                        // Copie du fichier vers le nouveau dossier
                        File destinationFile = new File(folderDate, inputFile.getName());
                        Files.copy(inputFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        nbrImageOk.incrementAndGet();
                    }
                    else {


                        String folderIgnoreDir = outputDirectoryPath + "/Photos_Autres";
                        File folderIgnore = new File(folderIgnoreDir);

                        if (!folderIgnore.exists()){
                            folderIgnore.mkdirs();
                        }

                        // Copie du fichier vers le nouveau dossier
                        File destinationFile = new File(folderIgnore, inputFile.getName());
                        Files.copy(inputFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        nbrImageNok.incrementAndGet();

                    }

                }

            } catch (ImageProcessingException | IOException e) {
                e.printStackTrace();
            }
        });

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Temps de traitement des images : " + elapsedTime + " ms");
        System.out.println(nbrImageOk + " images triées");
        System.out.println(nbrImageNok + " images non triées, elles sont rangées dans le dossier Photos_Autres");
    }
}
