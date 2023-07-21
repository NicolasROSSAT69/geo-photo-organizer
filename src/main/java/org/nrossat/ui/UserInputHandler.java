package org.nrossat.ui;

import java.util.Scanner;

public class UserInputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInputPath(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
