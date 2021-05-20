package com.hipravin.traderdashboard.api.dto;

import com.hipravin.tradersdashboard.moex.model.TradeGroup;

import java.math.BigDecimal;

public class TradeGroupDto {
    private BigDecimal minPrice = null;
    private BigDecimal maxPrice = null;
    private long totalQuantity = 0;
    private BigDecimal totalValue = BigDecimal.ZERO;
    private TradeDto biggestTrade = null;

    public static TradeGroupDto emptyTradeGroup() {
        return new TradeGroupDto();
    }

    public static TradeGroupDto of(TradeGroup tradeGroup) {
        TradeGroupDto tradeGroupDto = new TradeGroupDto();

        tradeGroupDto.minPrice = tradeGroup.getMinPrice();
        tradeGroupDto.maxPrice = tradeGroup.getMaxPrice();
        tradeGroupDto.totalQuantity = tradeGroup.getTotalQuantity();
        tradeGroupDto.totalValue = tradeGroup.getTotalValue();
        if(tradeGroup.getBiggestTrade() != null) {
            tradeGroupDto.biggestTrade = TradeDto.of(tradeGroup.getBiggestTrade());
        }

        return tradeGroupDto;
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

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public TradeDto getBiggestTrade() {
        return biggestTrade;
    }

    public void setBiggestTrade(TradeDto biggestTrade) {
        this.biggestTrade = biggestTrade;
    }
}
