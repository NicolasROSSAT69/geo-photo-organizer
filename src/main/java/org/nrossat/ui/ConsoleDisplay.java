package org.nrossat.ui;

public class ConsoleDisplay {

    public static void displayBanner() {
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

    public static void displayMessage(String message) {
        System.out.println(message);
    }

}
