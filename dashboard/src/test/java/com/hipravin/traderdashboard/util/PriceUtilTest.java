package com.hipravin.traderdashboard.util;

import com.hipravin.traderdashboard.api.dto.PriceGridDto;
import com.hipravin.tradersdashboard.MoexFileStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

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
}