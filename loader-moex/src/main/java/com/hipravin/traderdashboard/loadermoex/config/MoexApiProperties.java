package com.hipravin.traderdashboard.loadermoex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "moex.api.securities")
public class MoexApiProperties {
    private String tradesSubUrl;
    private String baseUrl;

    private TradesProperties trades;

    public static class TradesProperties {
        private Long pageSize;
        private List<String> emitentCodes;

        public Long getPageSize() {
            return pageSize;
        }

        public void setPageSize(Long pageSize) {
            this.pageSize = pageSize;
        }

        public List<String> getEmitentCodes() {
            return emitentCodes;
        }

        public void setEmitentCodes(List<String> emitentCodes) {
            this.emitentCodes = emitentCodes;
        }
    }

    public String getTradesSubUrl() {
        return tradesSubUrl;
    }

    public void setTradesSubUrl(String tradesSubUrl) {
        this.tradesSubUrl = tradesSubUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public TradesProperties getTrades() {
        return trades;
    }

    public void setTrades(TradesProperties trades) {
        this.trades = trades;
    }
}


//base-url: https://iss.moex.com/iss/
//        trades-sub-url: /trades.xml
//        trades:
//        page-size: 5000
//        emitent-codes: GAZP,TRMK,VTBR,CHM