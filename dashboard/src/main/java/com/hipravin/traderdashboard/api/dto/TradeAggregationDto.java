package com.hipravin.traderdashboard.api.dto;

import com.hipravin.tradersdashboard.moex.model.TradeFrame;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TradeAggregationDto {
    private String emCode;

    private LocalDateTime start;
    private LocalDateTime end;

    private long frameSizeMs;
    private List<TradeFrameDto> tradeFrames;

    public TradeAggregationDto() {
    }

    public static TradeAggregationDto of(String emCode, LocalDateTime start, LocalDateTime end, long frameSizeMs,
                                         List<TradeFrame> tradeFrames) {
        TradeAggregationDto tradeAggregationDto = new TradeAggregationDto();
        tradeAggregationDto.emCode = emCode;
        tradeAggregationDto.start = start;
        tradeAggregationDto.end = end;
        tradeAggregationDto.frameSizeMs = frameSizeMs;

        tradeAggregationDto.tradeFrames = tradeFrames.stream()
                .map(TradeFrameDto::of)
                .collect(Collectors.toList());

        return tradeAggregationDto;
    }

    public String getEmCode() {
        return emCode;
    }

    public void setEmCode(String emCode) {
        this.emCode = emCode;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getFrameSizeMs() {
        return frameSizeMs;
    }

    public void setFrameSizeMs(long frameSizeMs) {
        this.frameSizeMs = frameSizeMs;
    }

    public List<TradeFrameDto> getTradeFrames() {
        return tradeFrames;
    }

    public void setTradeFrames(List<TradeFrameDto> tradeFrames) {
        this.tradeFrames = tradeFrames;
    }
}
