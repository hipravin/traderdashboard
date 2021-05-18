package com.hipravin.traderdashboard.loadermoex.loader;

import com.hipravin.traderdashboard.loadermoex.config.LoaderProperties;
import com.hipravin.tradersdashboard.MoexApiXmlParser;
import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.utils.ZipFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MoexFileStorage {
    private static final DateTimeFormatter DATE_FILENAME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger log = LoggerFactory.getLogger(MoexFileStorage.class);

    private  final LoaderProperties loaderProperties;
    private final MoexApiXmlParser moexXmlParser;

    public MoexFileStorage(LoaderProperties loaderProperties, MoexApiXmlParser moexXmlParser) {
        this.loaderProperties = loaderProperties;
        this.moexXmlParser = moexXmlParser;
    }

    public void storeSinglePage(String emitentCode, LocalDate now, int pageNum, String content) throws IOException {
        Path pageDir = Paths.get(loaderProperties.getStorageDir(), emitentCode, now.format(DATE_FILENAME_FORMAT));
        createDirectoryIfNotExists(pageDir);

        String tradeTimestampMark = firstTradeTimestamp(content)
                .map(this::formatAsTimestampString)
                .orElse(UUID.randomUUID().toString());

        String fileName = emitentCode + "-" + pageNum + "-" + tradeTimestampMark;
        Path pageContentPathZip = Paths.get(pageDir.toString(),
                fileName + ".zip");

        log.info("Saving to file {}, size {}", pageContentPathZip, content.length());

        ZipFileUtils.writeStringToZip(pageContentPathZip, fileName + ".xml", content);
    }

    private String formatAsTimestampString(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }

    private Optional<ZonedDateTime> firstTradeTimestamp(String tradesPageContent) {
        Stream<Trade> trades = moexXmlParser.loadFromContent(tradesPageContent);
        return trades.findFirst().map(Trade::getTimestamp);
    }

//    public Stream<Path> findAll() throws IOException {
//        PathMatcher xmlExtensionMatcher = FileSystems.getDefault().getPathMatcher("glob:**.xml");
//
//        return Files.find(Paths.get(dataDir), Integer.MAX_VALUE,
//                (p, attr) -> xmlExtensionMatcher.matches(p) && attr.isRegularFile());
//    }
//
//    public Stream<Path> find(Long lastModifiedAfter) throws IOException {
//        PathMatcher xmlExtensionMatcher = FileSystems.getDefault().getPathMatcher("glob:**.xml");
//
//        return Files.find(Paths.get(dataDir), Integer.MAX_VALUE,
//                (p, attr) -> xmlExtensionMatcher.matches(p)
//                        && attr.isRegularFile()
//                        && attr.lastModifiedTime().toMillis() > lastModifiedAfter);
//    }
//
//    public Stream<Trade> findAllTrades() {
//        try {
//            return findAll().flatMap(f -> stockApiXmlParser.loadFromFile(f));
//        } catch (IOException e) {
//            throw new IllegalStateException("Failed to read trade repo: " + e.getMessage(), e);
//        }
//    }
//
//    public Stream<Trade> findTrades(Long lastModifiedAfter) {
//        try {
//            return find(lastModifiedAfter).flatMap(f -> stockApiXmlParser.loadFromFile(f));
//        } catch (IOException e) {
//            throw new IllegalStateException("Failed to read trade repo: " + e.getMessage(), e);
//        }
//    }

    private static void createDirectoryIfNotExists(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
        }
    }
}
