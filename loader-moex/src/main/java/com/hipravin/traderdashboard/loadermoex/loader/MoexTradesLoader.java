package com.hipravin.traderdashboard.loadermoex.loader;

import com.hipravin.traderdashboard.loadermoex.config.LoaderProperties;
import com.hipravin.traderdashboard.loadermoex.config.MoexApiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
public class MoexTradesLoader {
    Logger log = LoggerFactory.getLogger(MoexTradesLoader.class);
    private static final String NOT_EMPTY_PAGE_MARKER = "TRADENO=";

    private final MoexApiProperties moexApiProperties;
    private final LoaderProperties loaderProperties;
    private final MoexFileStorage moexFileStorage;
    private final RestTemplate restTemplate;

    public MoexTradesLoader(MoexApiProperties moexApiProperties,
                            LoaderProperties loaderProperties,
                            MoexFileStorage moexFileStorage,
                            RestTemplateBuilder restTemplateBuilder) {
        this.moexApiProperties = moexApiProperties;
        this.loaderProperties = loaderProperties;
        this.moexFileStorage = moexFileStorage;
        this.restTemplate = restTemplateBuilder.build();
    }

    public void loadAndStoreAllPagesForToday(String emitentCode, LocalDate now) throws IOException {
        String pageContent = null;

        for (int pageNum = 0; pageNum < moexApiProperties.getTrades().getMaxPageNum(); pageNum++) {
            pageContent = getPage(emitentCode, pageNum);
            log.info("Page {} loaded for {}, size: {}", pageNum, emitentCode, pageContent != null ? pageContent.length() : 0);

            if (pageContent == null) {
                log.error("Got null response, aborting. {} {} {}", emitentCode, pageNum, now);
                throw new IllegalStateException("Got null response for" + emitentCode + "-" + pageNum + " - " + now);
            }

            if (!emptyPage(pageContent)) {
                moexFileStorage.storeSinglePage(emitentCode, now, pageNum, pageContent);
            } else {
                log.info("Empty page, loading completed. {} {} {}", emitentCode, pageNum, now);
                break; //the only out condition.
            }

            shortPause();
        }
    }

    private void shortPause() {
        try {
            TimeUnit.MILLISECONDS.sleep(loaderProperties.getDelayBetweenMoexRequestsMs());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean emptyPage(String pageContent) {
        return pageContent == null || !pageContent.contains(NOT_EMPTY_PAGE_MARKER);
    }

    private String getPage(String emitentCode, int pageNum) {
        return get(moexApiProperties.buildTradesSinglePageUrl(emitentCode, pageNum));
    }

    public String get(String url) {
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Failed to load: " + url);
        }
        return response.getBody();
    }
}
