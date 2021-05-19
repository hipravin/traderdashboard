package com.hipravin.traderdashboard.loadermoex.loader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;

@SpringBootTest
class MoexTradesLoaderIT {

    @Autowired
    MoexTradesLoader moexTradesLoader;

    @Test
    void testLoadAllForTodayGazp() throws IOException {
        moexTradesLoader.loadAndStoreAllPagesForToday("GAZP", LocalDate.now());
    }
}