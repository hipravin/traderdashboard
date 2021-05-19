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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MoexFileStorage {
    private static final DateTimeFormatter DATE_FILENAME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String ZIP_EXTENSION = ".ZIP";

    private static final Logger log = LoggerFactory.getLogger(MoexFileStorage.class);

    private final LoaderProperties loaderProperties;
    private final MoexApiXmlParser moexXmlParser;

    public MoexFileStorage(LoaderProperties loaderProperties, MoexApiXmlParser moexXmlParser) {
        this.loaderProperties = loaderProperties;
        this.moexXmlParser = moexXmlParser;
    }

    /**
     * Returns stream of all trades according given parameters.
     *
     * @param emitentCode
     * @param forDate
     * @return
     */
    public Stream<Trade> findTrades(String emitentCode, LocalDate forDate) throws IOException {
        Path pageDir = Paths.get(loaderProperties.getStorageDir(), emitentCode, forDate.format(DATE_FILENAME_FORMAT));

        if (!Files.isDirectory(pageDir)) {
            return Stream.empty();
        }

        SortedMap<Integer, Path> tradePages = findTradePages(pageDir, emitentCode);

        Stream<Trade> combined = tradePages.entrySet().stream()
                .flatMap(e -> moexXmlParser.loadTradesFromFile(e.getValue()));

        return combined;
    }

    private SortedMap<Integer, Path> findTradePages(Path pageDir, String emitentCode) throws IOException {
        Pattern pagePattern = Pattern.compile("(?i)" + emitentCode + "-(\\d+)-.*.zip");//e.g. EMCODE-3-....zip
        Predicate<String> pagePatternMatches = pagePattern.asMatchPredicate();

        //[0->path0, 1-> path1, ...]
        SortedMap<Integer, Path> zippedTradeFilePages = Files.list(pageDir)
                .filter(Files::isRegularFile)
                .filter(p -> pagePatternMatches.test(p.getFileName().toString()))
                .filter(p -> p.getFileName().toString().toUpperCase().endsWith(ZIP_EXTENSION))
                .collect(Collectors.toMap(
                        p -> detectPageNumber(pagePattern, p.getFileName().toString()),
                        Function.identity(),
                        (p1, p2) -> p1, TreeMap::new));

        return zippedTradeFilePages;
    }

    private static int detectPageNumber(Pattern pattern, String s) {
        Matcher m = pattern.matcher(s);
        if(m.find() && m.group(1) != null) {
            return Integer.parseInt(m.group(1));
        } else {
            return -1;
        }
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

    private static void createDirectoryIfNotExists(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
        }
    }
}
