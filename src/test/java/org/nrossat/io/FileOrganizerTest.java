package org.nrossat.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FileOrganizerTest {

    private Path tempDirectory;
    private Path tempFile;

    @Before
    public void setUp() throws Exception {
        tempDirectory = Files.createTempDirectory("testDir");
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
        deleteRecursively(tempDirectory);
    }

    @Test
    public void testEnsureDirectoryExists_NewDirectory() {
        String newDirPath = tempDirectory.toString() + "/newDir";
        File result = FileOrganizer.ensureDirectoryExists(newDirPath);

        assertTrue(result.exists());
        assertTrue(result.isDirectory());
    }

    @Test
    public void testEnsureDirectoryExists_ExistingDirectory() {
        File existingDir = tempDirectory.toFile();
        assertTrue(existingDir.exists());

        File result = FileOrganizer.ensureDirectoryExists(tempDirectory.toString());

        assertTrue(result.exists());
        assertTrue(result.isDirectory());
        assertEquals(existingDir, result);
    }

    @Test
    public void testCopyFileToDirectory() {
        FileOrganizer.copyFileToDirectory(tempFile.toFile(), tempDirectory.toFile());

        File copiedFile = new File(tempDirectory.toFile(), tempFile.getFileName().toString());
        assertTrue(copiedFile.exists());
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
