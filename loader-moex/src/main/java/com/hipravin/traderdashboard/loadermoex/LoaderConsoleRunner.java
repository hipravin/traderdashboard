package com.hipravin.traderdashboard.loadermoex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoaderConsoleRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(LoaderConsoleRunner.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("Loader started...");


        log.info("Loader finished");
    }
}
