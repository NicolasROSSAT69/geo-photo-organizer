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

        //Chemin du répértoire des images de départ
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le chemin du repertoire des images : (/Users/nicolas/Pictures/monrepertoire)");
        String _inputDirectoryPath = sc.nextLine();
        String inputDirectoryPath = _inputDirectoryPath;

        //Chemin du dossier de sortie des images redimensionner
        System.out.println("Veuillez saisir le chemin du repertoire de sortie : (/Users/nicolas/Pictures/monrepertoiresortie)");
        String _outputDirectoryPath = sc.nextLine();
        String outputDirectoryPath = _outputDirectoryPath;

        File inputDirectory = new File(inputDirectoryPath);
        File outputDirectory = new File(outputDirectoryPath);

        //Vérif si le dossier de sortie des images redimensionnées existe sinon il le crée
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        // Liste les images présentes dans le dossier d'entrée qui ont comme extension (Jpg, Png, jpeg, heic)
        File[] inputFiles = inputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".heic"));

        if (inputFiles != null) {
            AtomicInteger nbrImageOk = new AtomicInteger(0);
            AtomicInteger nbrImageNok = new AtomicInteger(0);
            //Lancement du chrono
            long startTime = System.currentTimeMillis();

            Arrays.stream(inputFiles)
                    .parallel()
                    .forEach(inputFile -> {

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

            //Arrêt du chrono
            long endTime = System.currentTimeMillis();
            //Calcul de la différence
            long elapsedTime = endTime - startTime;
            System.out.println("Temps de traitement des images : " + elapsedTime + " ms");
            System.out.println(nbrImageOk + " images triées");
            System.out.println(nbrImageNok + " images non triées, elles sont rangées dans le dossier Photos_Autres");

        } else {
            System.out.println("Aucun fichier image trouvé dans le dossier d'entrée.");
        }

    }
}
