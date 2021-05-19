package com.hipravin.tradersdashboard.moex.model;

import java.time.LocalDateTime;

public class TradeFrame {
    private final LocalDateTime tradetimeStart;
    private final LocalDateTime tradetimeEnd;

    private final TradeGroup buyTradeGroup;
    private final TradeGroup sellTradeGroup;

    public static TradeFrame emptyTradeFrame(LocalDateTime tradetimeStart, LocalDateTime tradetimeEnd) {
        return new TradeFrame(tradetimeStart, tradetimeEnd, TradeGroup.emptyTradeGroup(), TradeGroup.emptyTradeGroup());
    }

    private TradeFrame(LocalDateTime tradetimeStart, LocalDateTime tradetimeEnd, TradeGroup buyTradeGroup, TradeGroup sellTradeGroup) {
        this.tradetimeStart = tradetimeStart;
        this.tradetimeEnd = tradetimeEnd;
        this.buyTradeGroup = buyTradeGroup;
        this.sellTradeGroup = sellTradeGroup;
    }

    public void addTrade(Trade trade) {
        if(trade.isBuy()) {
            buyTradeGroup.addTrade(trade);
        } else if(trade.isSell()) {
            sellTradeGroup.addTrade(trade);
        }
    }

    public LocalDateTime getTradetimeStart() {
        return tradetimeStart;
    }

    public LocalDateTime getTradetimeEnd() {
        return tradetimeEnd;
    }

    public TradeGroup getBuyTradeGroup() {
        return buyTradeGroup;
    }

    public TradeGroup getSellTradeGroup() {
        return sellTradeGroup;
    }
}
