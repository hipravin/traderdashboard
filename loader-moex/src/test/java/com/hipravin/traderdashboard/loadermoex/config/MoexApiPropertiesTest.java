package com.hipravin.traderdashboard.loadermoex.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("moex-default")
class MoexApiPropertiesTest {
    @Autowired
    private MoexApiProperties moexApiProperties;

    @Test
    void testMoexPropertiesLoaded() {
        assertEquals("https://iss.moex.com/iss/engines/stock/markets/shares/securities/", moexApiProperties.getBaseUrl());
        assertEquals("/trades.xml", moexApiProperties.getTrades().getSubUrl());

        MoexApiProperties.TradesProperties tradesProperties = moexApiProperties.getTrades();

        assertNotNull(tradesProperties);

        assertEquals(5000L, tradesProperties.getPageSize());
        assertEquals(51, tradesProperties.getEmitentCodes().size());
        assertEquals(1000, tradesProperties.getMaxPageNum());
        assertTrue(tradesProperties.getEmitentCodes().contains("GAZP"));

        assertEquals("https://iss.moex.com/iss/engines/stock/markets/shares/securities/GAZP/trades.xml?start=15000",
                moexApiProperties.buildTradesSinglePageUrl("GAZP", 3));
    }


}