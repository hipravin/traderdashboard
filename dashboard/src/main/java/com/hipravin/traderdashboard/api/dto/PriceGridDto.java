package com.hipravin.traderdashboard.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class PriceGridDto {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<BigDecimal> priceGrid;

    public PriceGridDto() {
    }

    public PriceGridDto(BigDecimal minPrice, BigDecimal maxPrice, List<BigDecimal> priceGrid) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.priceGrid = priceGrid;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<BigDecimal> getPriceGrid() {
        return priceGrid;
    }

    public void setPriceGrid(List<BigDecimal> priceGrid) {
        this.priceGrid = priceGrid;
    }
}
