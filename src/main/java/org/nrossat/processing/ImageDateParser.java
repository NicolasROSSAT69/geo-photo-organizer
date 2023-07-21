package org.nrossat.processing;

import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageDateParser {
    public static String parseDate(ExifIFD0Directory exifIFD0Directory) {
        String dateTime = exifIFD0Directory.getString(ExifIFD0Directory.TAG_DATETIME);
        if (dateTime != null) {
            dateTime = dateTime.split(" ")[0];
            dateTime = dateTime.replace(':', '-');
        }
        return dateTime;
    }
}
