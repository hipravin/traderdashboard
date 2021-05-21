package com.hipravin.traderdashboard;

import com.hipravin.tradersdashboard.MoexApiXmlParser;
import com.hipravin.tradersdashboard.MoexFileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.bind.JAXBException;

@SpringBootApplication
public class TraderdashboardApplication {
	@Value("${app.loader.storage-dir}")
	private String storageDir;

	@Bean
	MoexApiXmlParser moexApiXmlParser() throws JAXBException {
		return new MoexApiXmlParser();
	}

	@Bean
	MoexFileStorage moexFileStorage() throws JAXBException {
		return new MoexFileStorage(storageDir, moexApiXmlParser());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TraderdashboardApplication.class, args);
	}

}
