package org.nrossat.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageFileFilterTest {

    private Path tempDirectory;
    @Before
    public void setUp() throws Exception {
        tempDirectory = Files.createTempDirectory("imageFilterTestDir");

        Files.createFile(tempDirectory.resolve("test1.jpg"));
        Files.createFile(tempDirectory.resolve("test2.png"));
        Files.createFile(tempDirectory.resolve("test3.txt"));
        Files.createFile(tempDirectory.resolve("test4.heic"));
        Files.createFile(tempDirectory.resolve("test5.JPEG"));
        Files.createFile(tempDirectory.resolve("test6.PNG"));
        Files.createFile(tempDirectory.resolve("test7.HEIC"));
    }

    @After
    public void tearDown() throws IOException {
        deleteRecursively(tempDirectory);
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void testFilterImageFiles() {
        File[] filteredFiles = ImageFileFilter.filterImageFiles(tempDirectory.toFile());

        assertEquals(6, filteredFiles.length);

        for (File file : filteredFiles) {
            assertTrue(file.getName().toLowerCase().matches(ImageFileFilter.IMAGE_EXTENSIONS_PATTERN));
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                entries.forEach(entry -> {
                    try {
                        deleteRecursively(entry);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        Files.deleteIfExists(path);
    }

}
