package com.hipravin.traderdashboard.loadermoex;

import com.hipravin.traderdashboard.loadermoex.config.LoaderProperties;
import com.hipravin.traderdashboard.loadermoex.config.MoexApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        MoexApiProperties.class,
        LoaderProperties.class})
public class LoaderMoexApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoaderMoexApplication.class, args);
    }
}
