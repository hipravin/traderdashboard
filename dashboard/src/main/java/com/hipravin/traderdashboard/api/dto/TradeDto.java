package com.hipravin.traderdashboard.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hipravin.tradersdashboard.moex.jaxb.RowType;
import com.hipravin.tradersdashboard.moex.model.Trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TradeDto {
    private BigDecimal price;
    private long quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timestamp;
    private BigDecimal value;

    public static TradeDto of(Trade trade) {
        TradeDto tradeDto = new TradeDto();

        tradeDto.price = trade.getPrice();
        tradeDto.quantity = trade.getQuantity();
        tradeDto.timestamp = trade.getTimestamp();
        tradeDto.value = trade.getValue();
        return tradeDto;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
