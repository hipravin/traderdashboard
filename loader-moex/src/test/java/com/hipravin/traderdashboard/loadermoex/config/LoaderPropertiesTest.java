package com.hipravin.traderdashboard.loadermoex.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class LoaderPropertiesTest {
    @Autowired
    LoaderProperties loaderProperties;

    @Test
    void testPropertiesLoaded() {
        assertEquals("C:/dev/moex-data/data", loaderProperties.getStorageDir());
    }
}