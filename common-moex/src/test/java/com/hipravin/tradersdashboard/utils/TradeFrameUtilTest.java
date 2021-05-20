package com.hipravin.tradersdashboard.utils;

import com.hipravin.tradersdashboard.MoexApiXmlParser;
import com.hipravin.tradersdashboard.MoexFileStorage;
import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.moex.model.TradeGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeFrameUtilTest {
    static MoexApiXmlParser moexApiXmlParser;
    static MoexFileStorage moexFileStorage;
    static LocalDate sampleVtbrDate = LocalDate.of(2021, 5, 18);

    @BeforeAll
    static void setUp() throws JAXBException {
        moexApiXmlParser = new MoexApiXmlParser();
        moexFileStorage = new MoexFileStorage("src/test/resources/samples", moexApiXmlParser);
    }

    @Test
    void testAllToSingleFrame() throws IOException {
        Stream<Trade> trades = moexFileStorage.findTrades("VTBR", sampleVtbrDate);
        List<Trade> tradeList = trades.collect(Collectors.toList());

        List<TradeFrame> tfs = TradeFrameUtil.mergeTradesToTradeFrames(tradeList.stream(),
                sampleVtbrDate.atTime(9, 50),
                sampleVtbrDate.atTime(23, 50),
                Duration.ofDays(1));

        assertEquals(1, tfs.size());

        TradeFrame tf = tfs.get(0);
        BigDecimal sumValue = tradeList.stream()
                .map(Trade::getValue)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        assertEquals(sumValue, tf.getBuyTradeGroup().getTotalValue().add(tf.getSellTradeGroup().getTotalValue()));
    }

    @Test
    void test8k() throws IOException {
        Stream<Trade> trades = moexFileStorage.findTrades("VTBR", sampleVtbrDate);
        List<Trade> tradeList = trades.collect(Collectors.toList());

        List<TradeFrame> tfs = TradeFrameUtil.mergeTradesToTradeFrames(tradeList.stream(),
                sampleVtbrDate.atTime(9, 50),
                sampleVtbrDate.atTime(23, 50),
                Duration.ofMillis(6300));

        assertEquals(8000, tfs.size());
    }

    @Test
    void testAllToSingleGroup() throws IOException {
        Stream<Trade> trades = moexFileStorage.findTrades("VTBR", sampleVtbrDate);
        List<Trade> tradeList = trades.collect(Collectors.toList());

        TradeGroup tradeGroup = new TradeGroup();
        tradeList.forEach(tradeGroup::addTrade);

        long sumQuantity = tradeList.stream().mapToLong(Trade::getQuantity).sum();
        BigDecimal sumValue = tradeList.stream()
                .map(Trade::getValue)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        assertEquals(sumQuantity, tradeGroup.getTotalQuantity());
        assertEquals(sumValue, tradeGroup.getTotalValue());

        BigDecimal minPrice = tradeList.stream().min(Comparator.comparing(Trade::getPrice)).map(Trade::getPrice).orElseThrow();
        BigDecimal maxPrice = tradeList.stream().max(Comparator.comparing(Trade::getPrice)).map(Trade::getPrice).orElseThrow();

        assertEquals(tradeGroup.getMinPrice(), minPrice);
        assertEquals(tradeGroup.getMaxPrice(), maxPrice);

        Trade biggestTrade = tradeList.stream().max(Comparator.comparing(Trade::getValue)).orElseThrow();
        assertEquals(biggestTrade, tradeGroup.getBiggestTrade());
    }
}