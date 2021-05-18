package com.hipravin.traderdashboard.loadermoex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "moex.api.securities")
public class MoexApiProperties {
    private String baseUrl;

    private TradesProperties trades;

    public String buildTradesSinglePageUrl(String emitentCode, int pageNum) {
        return baseUrl + emitentCode + trades.subUrl +  "?start=" + pageNum * trades.pageSize;
    }

    public static class TradesProperties {
        private Long pageSize;
        private String subUrl;
        private List<String> emitentCodes;
        private long maxPageNum;

        public String getSubUrl() {
            return subUrl;
        }

        public void setSubUrl(String subUrl) {
            this.subUrl = subUrl;
        }

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

        public long getMaxPageNum() {
            return maxPageNum;
        }

        public void setMaxPageNum(long maxPageNum) {
            this.maxPageNum = maxPageNum;
        }
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