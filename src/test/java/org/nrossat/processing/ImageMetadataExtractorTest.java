package org.nrossat.processing;

import com.drew.metadata.exif.ExifIFD0Directory;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ImageMetadataExtractorTest {
    @Test
    public void testExtractMetadata_WithExif() throws Exception {
        // Préparation
        URL imageUrl = getClass().getClassLoader().getResource("sample_with_exif.JPG");
        File imageFile = new File(imageUrl.getFile());

        // Action
        ExifIFD0Directory exifDir = ImageMetadataExtractor.extractMetadata(imageFile);

        // Vérification
        assertNotNull(exifDir);
    }

    @Test
    public void testExtractMetadata_WithoutExif() throws Exception {
        // Préparation
        URL imageUrl = getClass().getClassLoader().getResource("sample_without_exif.jpg");
        File imageFile = new File(imageUrl.getFile());

        // Action
        ExifIFD0Directory exifDir = ImageMetadataExtractor.extractMetadata(imageFile);

        // Vérification
        assertNull(exifDir);
    }
}
