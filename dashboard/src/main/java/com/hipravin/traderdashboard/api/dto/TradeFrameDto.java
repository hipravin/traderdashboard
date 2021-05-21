package com.hipravin.traderdashboard.api.dto;

import com.hipravin.tradersdashboard.moex.model.TradeFrame;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TradeFrameDto {
    private LocalDateTime tradetimeStart;
    private LocalDateTime tradetimeEnd;

    private TradeGroupDto buyTradeGroup;
    private TradeGroupDto sellTradeGroup;


    public static TradeFrameDto of(TradeFrame tradeFrame) {
        TradeFrameDto tradeFrameDto = new TradeFrameDto();
        tradeFrameDto.tradetimeStart = tradeFrame.getTradetimeStart();
        tradeFrameDto.tradetimeEnd = tradeFrame.getTradetimeEnd();

        tradeFrameDto.buyTradeGroup = TradeGroupDto.of(tradeFrame.getBuyTradeGroup());
        tradeFrameDto.sellTradeGroup = TradeGroupDto.of(tradeFrame.getSellTradeGroup());

        return tradeFrameDto;
    }

    public LocalDateTime getTradetimeStart() {
        return tradetimeStart;
    }

    public LocalDateTime getTradetimeEnd() {
        return tradetimeEnd;
    }

    public TradeGroupDto getBuyTradeGroup() {
        return buyTradeGroup;
    }

    public TradeGroupDto getSellTradeGroup() {
        return sellTradeGroup;
    }
}
