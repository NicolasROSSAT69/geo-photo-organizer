package org.nrossat.io;

import java.io.File;

public class ImageFileFilter {
    public static File[] filterImageFiles(File inputDirectory) {
        return inputDirectory.listFiles((dir, name) ->
                name.toLowerCase().matches(".*\\.(jpg|png|jpeg|heic)$")
        );
    }
}
