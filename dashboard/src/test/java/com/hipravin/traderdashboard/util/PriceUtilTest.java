package com.hipravin.traderdashboard.util;

import com.hipravin.traderdashboard.api.dto.PriceGridDto;
import com.hipravin.traderdashboard.api.dto.TradeAggregationDto;
import com.hipravin.tradersdashboard.MoexFileStorage;
import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.utils.TradeFrameUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceUtilTest {
    @Autowired
    MoexFileStorage moexFileStorage;

    @Test
    void testSamplePriceGrid() {
        BigDecimal minPrice = new BigDecimal("0.045125");
        BigDecimal maxPrice = new BigDecimal("0.047450");
        PriceGridDto priceGridDto = PriceUtil.definePriceGrid(minPrice, maxPrice);

        assertTrue(minPrice.compareTo(priceGridDto.getMinPrice()) >= 0);
        assertTrue(maxPrice.compareTo(priceGridDto.getMaxPrice()) <= 0);

        System.out.println(priceGridDto.getPriceGrid());

    }

    @Test
    void testSamplePriceGrid2() {
        BigDecimal minPrice = new BigDecimal("219.25");
        BigDecimal maxPrice = new BigDecimal("227.99");
        PriceGridDto priceGridDto = PriceUtil.definePriceGrid(minPrice, maxPrice);

        assertTrue(minPrice.compareTo(priceGridDto.getMinPrice()) >= 0);
        assertTrue(maxPrice.compareTo(priceGridDto.getMaxPrice()) <= 0);

        System.out.println(priceGridDto.getPriceGrid());

    }

    @Test
    void testSamplePriceGrid3() {
        BigDecimal minPrice = new BigDecimal("22110");
        BigDecimal maxPrice = new BigDecimal("26650");
        PriceGridDto priceGridDto = PriceUtil.definePriceGrid(minPrice, maxPrice);

        assertTrue(minPrice.compareTo(priceGridDto.getMinPrice()) >= 0);
        assertTrue(maxPrice.compareTo(priceGridDto.getMaxPrice()) <= 0);

        System.out.println(priceGridDto.getPriceGrid());

    }

//    @Test
//    void testPgGazp20() throws IOException {
//        LocalDate day= LocalDate.of(2021, 5,20);
//        Stream<Trade> trades = moexFileStorage.findTrades("GAZP", day);
//
////        trades.filter(t -> t.getPrice().compareTo(new BigDecimal("265.97"))>0)
////                .forEach(t -> System.out.println(t));
//
//
//        LocalDateTime start = day.atTime(9, 50);
//        LocalDateTime end = day.atTime(23, 59);
//
//        List<TradeFrame> frames = TradeFrameUtil.mergeTradesToTradeFrames(trades, start, end, Duration.ofMillis(6300));
//
//        TradeAggregationDto result = TradeAggregationDto.of("GAZP", start, end, 6300, frames);
//
//
//
//
//
//    }
}