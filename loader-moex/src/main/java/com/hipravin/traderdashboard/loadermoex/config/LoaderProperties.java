package com.hipravin.traderdashboard.loadermoex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.loader")
public class LoaderProperties {
    private String storageDir;

    private long delayBetweenMoexRequestsMs;
    private long delayBetweenEmitentLoadMs;

    public String getStorageDir() {
        return storageDir;
    }

    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

    public long getDelayBetweenMoexRequestsMs() {
        return delayBetweenMoexRequestsMs;
    }

    public void setDelayBetweenMoexRequestsMs(long delayBetweenMoexRequestsMs) {
        this.delayBetweenMoexRequestsMs = delayBetweenMoexRequestsMs;
    }

    public long getDelayBetweenEmitentLoadMs() {
        return delayBetweenEmitentLoadMs;
    }

    public void setDelayBetweenEmitentLoadMs(long delayBetweenEmitentLoadMs) {
        this.delayBetweenEmitentLoadMs = delayBetweenEmitentLoadMs;
    }
}
