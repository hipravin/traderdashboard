package com.hipravin.tradersdashboard.moex.model;

import java.math.BigDecimal;

public class TradeGroup {
    private BigDecimal minPrice = null;
    private BigDecimal maxPrice = null;
    private long totalQuantity = 0;
    private BigDecimal totalValue = BigDecimal.ZERO;
    private Trade biggestTrade = null;

    public static TradeGroup emptyTradeGroup() {
        return new TradeGroup();
    }

    public void addTrade(Trade trade) {
        if(biggestTrade == null || biggestTrade.getValue().compareTo(trade.getValue()) < 0) {
            biggestTrade = trade;
        }
        totalQuantity += trade.getQuantity();
        totalValue = totalValue.add(trade.getValue());
        if(minPrice == null || minPrice.compareTo(trade.getPrice()) > 0) {
            minPrice = trade.getPrice();
        }
        if(maxPrice == null || maxPrice.compareTo(trade.getPrice()) < 0) {
            maxPrice = trade.getPrice();
        }
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

    public Trade getBiggestTrade() {
        return biggestTrade;
    }

    public void setBiggestTrade(Trade biggestTrade) {
        this.biggestTrade = biggestTrade;
    }
}
