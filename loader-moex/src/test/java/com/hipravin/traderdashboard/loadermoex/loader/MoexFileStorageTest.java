package com.hipravin.traderdashboard.loadermoex.loader;

import com.hipravin.tradersdashboard.moex.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MoexFileStorageTest {
    @Autowired
    MoexFileStorage moexFileStorage;

    @Test
    void loadNotFoundDayVtbr() throws IOException {
        Stream<Trade> trades = moexFileStorage.findTrades("VTBR", LocalDate.of(2021, 5, 1));
        assertEquals(0, trades.count());
    }

    @Test
    void loadSampleDayVtbr() throws IOException {
        Stream<Trade> trades = moexFileStorage.findTrades("VTBR", LocalDate.of(2021, 5, 18));
        List<Trade> tradeList = trades.collect(Collectors.toList());

        //total
        assertEquals(new BigDecimal("9686192052.15"),
                tradeList.stream().map(Trade::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        assertEquals(172_981, tradeList.size());

        //trades bigger than 1k
        BigDecimal treshhold1 = new BigDecimal("1000");
        assertEquals(134594, countFiltered(tradeList, t -> t.getValue().compareTo(treshhold1) > 0));
        assertEquals(9_664_561_765L, sumValue(tradeList, t -> t.getValue().compareTo(treshhold1) > 0));

        //trades bigger than 5k
        BigDecimal treshhold2 = new BigDecimal("5000");
        assertEquals(89427, countFiltered(tradeList, t -> t.getValue().compareTo(treshhold2) > 0));//half or trades
        assertEquals(9_526_791_743L, sumValue(tradeList, t -> t.getValue().compareTo(treshhold2) > 0));

        //trades bigger than 5k
        BigDecimal treshhold3 = new BigDecimal("10000");
        assertEquals(68806, countFiltered(tradeList, t -> t.getValue().compareTo(treshhold3) > 0));//half or trades
        assertEquals(9_382_585_698L, sumValue(tradeList, t -> t.getValue().compareTo(treshhold3) > 0));

        //trades bigger than 50k
        BigDecimal treshhold4 = new BigDecimal("50000");
        assertEquals(26668, countFiltered(tradeList, t -> t.getValue().compareTo(treshhold4) > 0));//half or trades
        assertEquals(8_256_855_364L, sumValue(tradeList, t -> t.getValue().compareTo(treshhold4) > 0));

        //some investigations
        long distinctCounts = tradeList.stream().limit(10_000)
                .map(Trade::getQuantity)
                .distinct()
                .count();

        assertEquals(626, distinctCounts);
    }

    long countFiltered(List<Trade> trades, Predicate<? super Trade> tradePredicate) {
        return trades.stream().filter(tradePredicate).count();
    }

    long sumValue(List<Trade> trades, Predicate<? super Trade> tradePredicate) {
        return trades.stream().filter(tradePredicate)
                .map(Trade::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .longValue();
    }

}