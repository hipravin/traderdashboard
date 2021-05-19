package com.hipravin.tradersdashboard.moex.model;


import com.hipravin.tradersdashboard.moex.jaxb.RowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Trade {
    private String id;
    private long tradeno;
    private LocalTime tradetime;
    private String boardid;
    private String secid;
    private BigDecimal price;
    private long quantity;
    private String period;
    private String tradetimegrp;
    private ZonedDateTime timestamp;
    private String buysell;
    private BigDecimal value;

//        <row TRADENO="3080550458" TRADETIME="09:59:50" BOARDID="TQBR"
//        SECID="AFKS" PRICE="17.774" QUANTITY="1" VALUE="1777.4" PERIOD="S"
//        TRADETIME_GRP="959" SYSTIME="2020-01-27 09:59:50" BUYSELL="B" DECIMALS="3" />
    public static Trade of(RowType row) {
        Trade trade = new Trade();
        trade.tradeno = Long.parseLong(row.getTRADENO());
        trade.tradetime = LocalTime.parse(row.getTRADETIME(), DateTimeFormatter.ISO_LOCAL_TIME);
        trade.boardid = row.getBOARDID();
        trade.secid = row.getSECID();
        trade.price = new BigDecimal(row.getPRICE());
        trade.quantity = Long.parseLong(row.getQUANTITY());
        trade.value = new BigDecimal(row.getValue());
        trade.tradetimegrp = row.getTRADETIMEGRP();
        trade.timestamp = LocalDateTime.parse(row.getSYSTIME(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.of("Europe/Moscow"));
        trade.buysell = row.getBUYSELL();

        trade.id = trade.secid + "-" + trade.tradeno + "-" + row.getSYSTIME();
        return trade;
    }

    public long getTradeno() {
        return tradeno;
    }

    public void setTradeno(long tradeno) {
        this.tradeno = tradeno;
    }

    public LocalTime getTradetime() {
        return tradetime;
    }

    public void setTradetime(LocalTime tradetime) {
        this.tradetime = tradetime;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTradetimegrp() {
        return tradetimegrp;
    }

    public void setTradetimegrp(String tradetimegrp) {
        this.tradetimegrp = tradetimegrp;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getBuysell() {
        return buysell;
    }

    public void setBuysell(String buysell) {
        this.buysell = buysell;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBuy() {
        return "B".equalsIgnoreCase(buysell);
    }
    public boolean isSell() {
        return "S".equalsIgnoreCase(buysell);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", tradeno=" + tradeno +
                ", tradetime=" + tradetime +
                ", boardid='" + boardid + '\'' +
                ", secid='" + secid + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", period='" + period + '\'' +
                ", tradetimegrp='" + tradetimegrp + '\'' +
                ", timestamp=" + timestamp +
                ", buysell='" + buysell + '\'' +
                ", value=" + value +
                '}';
    }
}
