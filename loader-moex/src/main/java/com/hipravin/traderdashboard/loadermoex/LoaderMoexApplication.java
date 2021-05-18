package com.hipravin.traderdashboard.loadermoex;

import com.hipravin.traderdashboard.loadermoex.config.LoaderProperties;
import com.hipravin.traderdashboard.loadermoex.config.MoexApiProperties;
import com.hipravin.tradersdashboard.MoexApiXmlParser;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.xml.bind.JAXBException;

@SpringBootApplication
@EnableConfigurationProperties({
        MoexApiProperties.class,
        LoaderProperties.class})
public class LoaderMoexApplication {

    @Bean
    MoexApiXmlParser moexApiXmlParser() throws JAXBException {
        return new MoexApiXmlParser();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(LoaderMoexApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
