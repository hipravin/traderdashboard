package com.hipravin.tradersdashboard;

import com.hipravin.tradersdashboard.moex.model.Trade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MoexApiXmlParserTest {
    static MoexApiXmlParser moexApiXmlParser;
    Path sampleTradesFile = Paths.get( "src/test/resources", "samples", "GAZP-0-2020-11-24-09-59-57.xml");

    @BeforeAll
    static void setUp() throws JAXBException {
        moexApiXmlParser = new MoexApiXmlParser();
    }

    @Test
    void testParseSamplePlain() {
        assertTrue(Files.isRegularFile(sampleTradesFile));

        Stream<Trade> trades = moexApiXmlParser.loadFromFilePlain(sampleTradesFile);

        long count = trades.peek(t -> {
            assertNotNull(t.getBuysell());
            assertNotNull(t.getPrice());
            assertTrue(t.getQuantity() != 0);
            assertNotNull(t.getValue());
            assertNotNull(t.getTradetime());
        }).count();

        assertEquals(5000, count);
    }

    @Test
    @Disabled
    void sampleZip() throws IOException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        URI uri = URI.create("jar:file:/dev/traderdashboard/common-moex/src/test/resources/samples/GAZP-0-2020-11-24-09-59-57.zip");

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path pathInZipfile = zipfs.getPath("/GAZP-0-2020-11-24-09-59-57.xml");
            Files.writeString(pathInZipfile, Files.readString(sampleTradesFile));
        }
    }

    @Test
    @Disabled
    void sampleZip2() throws IOException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        Path sampleTradesFileZip = Paths.get( "src/test/resources", "samples", "GAZP-0-2020-11-24-09-59-57.zip");

        try (FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + sampleTradesFileZip.toUri().toString()), env)) {
            Path pathInZipfile = zipfs.getPath("/GAZP-0-2020-11-24-09-59-57.xml");
            Files.writeString(pathInZipfile, Files.readString(sampleTradesFile));
        }
    }

    @Test
    @Disabled
    void sampleZipRead() throws IOException {

        Map<String, String> env = new HashMap<>();
        Path sampleTradesFileZip = Paths.get( "src/test/resources", "samples", "GAZP-0-2020-11-24-09-59-57.zip");

        try (FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + sampleTradesFileZip.toUri().toString()), env)) {
            Path pathInZipfile = zipfs.getPath("/GAZP-0-2020-11-24-09-59-57.xml");
            assertEquals(Files.readString(sampleTradesFile, StandardCharsets.UTF_8),
                    Files.readString(pathInZipfile));
        }
    }
}