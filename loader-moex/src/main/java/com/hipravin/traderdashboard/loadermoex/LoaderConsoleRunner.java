package com.hipravin.traderdashboard.loadermoex;

import com.hipravin.traderdashboard.loadermoex.config.LoaderProperties;
import com.hipravin.traderdashboard.loadermoex.config.MoexApiProperties;
import com.hipravin.traderdashboard.loadermoex.loader.MoexTradesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
@Profile("loadall")
public class LoaderConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(LoaderConsoleRunner.class);

    private final LoaderProperties loaderProperties;
    private final MoexApiProperties moexApiProperties;
    private final MoexTradesLoader moexTradesLoader;

    public LoaderConsoleRunner(LoaderProperties loaderProperties,
                               MoexApiProperties moexApiProperties,
                               MoexTradesLoader moexTradesLoader) {
        this.loaderProperties = loaderProperties;
        this.moexApiProperties = moexApiProperties;
        this.moexTradesLoader = moexTradesLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loader started...");

        for (String emitentCode : moexApiProperties.getTrades().getEmitentCodes()) {
            moexTradesLoader.loadAndStoreAllPagesForToday(emitentCode, LocalDate.now());
            log.info(" ============================ COMPLETED {} ===================", emitentCode);
            shortPause();
        }

        log.info("Loader finished");
    }

    private void shortPause() {
        try {
            TimeUnit.MILLISECONDS.sleep(loaderProperties.getDelayBetweenMoexRequestsMs());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
