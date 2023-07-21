package org.nrossat.processing;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import java.io.File;

public class ImageMetadataExtractor {
    public static ExifIFD0Directory extractMetadata(File imageFile) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
        return metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
    }
}
