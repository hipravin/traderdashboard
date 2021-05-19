package com.hipravin.tradersdashboard.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ZipFileUtils {
    private ZipFileUtils() {
    }

    public static String readStringFromZip(Path zipFilePath, String textFileNameInZip) throws IOException {
        Map<String, String> env = new HashMap<>();

        try (FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + zipFilePath.toUri().toString()), env)) {
            Path pathInZipfile = zipfs.getPath(textFileNameInZip);
            return Files.readString(pathInZipfile, StandardCharsets.UTF_8);
        }
    }

    public static void writeStringToZip(Path zipFilePath, String textFileNameInZip, String content) throws IOException {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try (FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + zipFilePath.toUri()), env)) {
            Path pathInZipfile = zipfs.getPath(textFileNameInZip);
            Files.writeString(pathInZipfile, content, StandardCharsets.UTF_8);
        }
    }
}
