package org.nrossat.io;

import java.io.File;

public class ImageFileFilter {

    public static final String IMAGE_EXTENSIONS_PATTERN = ".*\\.(jpg|png|jpeg|heic)$";
    public static File[] filterImageFiles(File inputDirectory) {
        return inputDirectory.listFiles((dir, name) ->
                name.toLowerCase().matches(IMAGE_EXTENSIONS_PATTERN)
        );
    }
}
