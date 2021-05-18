package com.hipravin.tradersdashboard;

import com.hipravin.tradersdashboard.moex.jaxb.DocumentType;
import com.hipravin.tradersdashboard.moex.jaxb.RowType;
import com.hipravin.tradersdashboard.moex.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MoexApiXmlParser {
    private static Logger log = LoggerFactory.getLogger(MoexApiXmlParser.class);

    private JAXBContext context;

    public MoexApiXmlParser() throws JAXBException {
        this.context = JAXBContext.newInstance("com.hipravin.tradersdashboard.moex.jaxb");
    }

    public DocumentType parse(String content) throws JAXBException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(new StringReader(content)), DocumentType.class).getValue();
    }

    public Stream<Trade> loadFromFilePlain(Path file) {
        try {
            log.debug("Loading from file: " + file);
            return loadFromContent(Files.readString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public Stream<Trade> loadFromContent(String content) {
        try {
            Stream<RowType> tradeRows = parse(content)
                    .getData().stream().filter(td -> "trades".equalsIgnoreCase(td.getId()))
                    .findAny().map(td -> td.getRows().getRow().stream())
                    .orElse(Stream.of());

            return tradeRows.map(Trade::of);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

}
