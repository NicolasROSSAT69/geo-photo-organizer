package org.nrossat.processing;

import static org.junit.Assert.assertTrue;

import com.drew.metadata.exif.ExifIFD0Directory;
import org.junit.Assert;
import org.junit.Test;
import org.nrossat.processing.ImageDateParser;

import static org.mockito.Mockito.*;

public class ImageDateParserTest {
    @Test
    public void testParseDate() {
        ExifIFD0Directory exifDir = mock(ExifIFD0Directory.class);
        when(exifDir.getString(ExifIFD0Directory.TAG_DATETIME)).thenReturn("2023:07:20 12:34:56");

        String parsedDate = ImageDateParser.parseDate(exifDir);

        Assert.assertEquals("2023-07-20", parsedDate);
    }
}
