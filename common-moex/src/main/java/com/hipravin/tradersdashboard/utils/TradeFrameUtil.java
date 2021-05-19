package com.hipravin.tradersdashboard.utils;

import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.moex.model.TradeGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TradeFrameUtil {
    public static List<TradeFrame> mergeTradesToTradeFrames(
            Stream<Trade> trades, LocalDateTime start, LocalDateTime end, TemporalAmount frameSize) {

        NavigableMap<LocalDateTime, TradeFrame> frames = emptyFrames(start, end, frameSize);

        trades.forEach(t -> {
            Map.Entry<LocalDateTime, TradeFrame> floorEntry = frames.floorEntry(t.getTimestamp().toLocalDateTime());
            if(floorEntry != null) {
                floorEntry.getValue().addTrade(t);
            }
        });

        return new ArrayList<>(frames.values());
    }

    private static NavigableMap<LocalDateTime, TradeFrame> emptyFrames(LocalDateTime start, LocalDateTime end, TemporalAmount frameSize) {
        NavigableMap<LocalDateTime, TradeFrame> frameMap = new TreeMap<>();

        return Stream.iterate(start, d -> d.isBefore(end), d -> d.plus(frameSize))
                .map(beg -> TradeFrame.emptyTradeFrame(beg, beg.plus(frameSize)))
                .collect(Collectors.toMap(
                        TradeFrame::getTradetimeStart, Function.identity(), (a,b) -> a, TreeMap::new));

    }

}
