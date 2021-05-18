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
        assertEquals("/trades.xml", moexApiProperties.getTradesSubUrl());

        MoexApiProperties.TradesProperties tradesProperties = moexApiProperties.getTrades();

        assertNotNull(tradesProperties);

        assertEquals(5000L, tradesProperties.getPageSize());
        assertEquals(48, tradesProperties.getEmitentCodes().size());
        assertTrue(tradesProperties.getEmitentCodes().contains("GAZP"));
    }
}