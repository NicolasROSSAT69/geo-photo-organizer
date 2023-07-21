package org.nrossat;

import org.nrossat.io.ImageFileFilter;
import org.nrossat.processing.Chronometer;
import org.nrossat.processing.ImageProcessor;
import org.nrossat.ui.ConsoleDisplay;
import org.nrossat.ui.UserInputHandler;

import java.io.File;

public class App 
{
    private static final String MESSAGE_CHEMIN_IMG_IN_PATTERN = "Veuillez saisir le chemin du repertoire des images :";
    private static final String MESSAGE_CHEMIN_IMG_OUT_PATTERN = "Veuillez saisir le chemin du repertoire de sortie :";

    public static void main( String[] args )
    {

        ConsoleDisplay.displayBanner();

        String inputDirectoryPath = UserInputHandler.getInputPath(MESSAGE_CHEMIN_IMG_IN_PATTERN);
        String outputDirectoryPath = UserInputHandler.getInputPath(MESSAGE_CHEMIN_IMG_OUT_PATTERN);

        File[] inputFiles = ImageFileFilter.filterImageFiles(new File(inputDirectoryPath));
        if (inputFiles == null || inputFiles.length == 0) {
            System.out.println("Aucun fichier image trouvé dans le dossier d'entrée.");
            return;
        }

        Chronometer chronometer = new Chronometer();
        chronometer.start();
        ImageProcessor.organizeByDate(inputFiles, outputDirectoryPath);
        ConsoleDisplay.displayMessage("Temps de traitement des images : " + chronometer.stop() + " ms");

    }

}
