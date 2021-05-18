package com.hipravin.tradersdashboard.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ZipFileUtilsTest {
    String sampleFileName = "GAZP-0-2020-11-24-09-59-57";
    Path sampleTradesFile = Paths.get( "src/test/resources", "samples", "GAZP-0-2020-11-24-09-59-57.xml");

    @Test
    void testWriteRead() throws IOException {
        Path tempFile = Paths.get( "src/test/resources", "samples", "GAZP-0-2020-11-24-09-59-57.zip");
        String sampleContent = Files.readString(sampleTradesFile, StandardCharsets.UTF_8);

        ZipFileUtils.writeStringToZip(tempFile, sampleFileName + ".xml", sampleContent);
        assertEquals(sampleContent, ZipFileUtils.readStringFromZip(tempFile, sampleFileName + ".xml"));
        Files.deleteIfExists(tempFile);
    }
}