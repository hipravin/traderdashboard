package com.hipravin.traderdashboard.util;

import com.hipravin.traderdashboard.api.dto.PriceGridDto;
import com.hipravin.tradersdashboard.TradesNotFoundException;
import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.moex.model.TradeGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public abstract class PriceUtil {

    private static final int STEPS = 20;

    private static final PriceGridDto DEFAULT_EMPTY_GRID = new PriceGridDto(BigDecimal.ONE, BigDecimal.TEN, Collections.emptyList());

    private PriceUtil() {
    }

    public static PriceGridDto definePriceGrid(List<Trade> trades, int desiredLabelCount) {
        if (trades.isEmpty()) {
            return DEFAULT_EMPTY_GRID;
        } else {
            NavigableSet<BigDecimal> prices = trades.stream()
                    .map(t -> t.getPrice())
                    .collect(Collectors.toCollection(TreeSet::new));


            SortedSet<BigDecimal> labelPrices = new TreeSet<>();

            final BigDecimal minPrice = prices.first();
            final BigDecimal maxPrice = prices.last();

            BigDecimal delta = maxPrice.subtract(minPrice);
            BigDecimal step = delta.divide(new BigDecimal(STEPS), delta.scale() + 4, RoundingMode.FLOOR);

            labelPrices.add(minPrice);
            labelPrices.add(maxPrice);

            for (int i = 1; i < desiredLabelCount; i++) {
                BigDecimal candidate = minPrice.add(step.multiply(BigDecimal.valueOf(i)));

                BigDecimal neareastUp = ofNullable(prices.ceiling(candidate)).orElse(candidate);
                BigDecimal neareastDown = ofNullable(prices.floor(candidate)).orElse(candidate);

                BigDecimal deltaUp = neareastUp.subtract(candidate);
                BigDecimal deltaDown = candidate.subtract(neareastDown);

                BigDecimal nearest = deltaUp.compareTo(deltaDown) > 0 ? neareastDown : neareastUp;

                labelPrices.add(nearest);
            }

            return new PriceGridDto(minPrice, maxPrice, new ArrayList<>(labelPrices));
        }
    }


    public static PriceGridDto definePriceGrid(List<TradeFrame> tradeFrames) {
        if (!tradeFrames.isEmpty()) {
            BigDecimal minPrice = tradeGroupStream(tradeFrames)
                    .filter(tg -> tg.getMinPrice() != null && !tg.getMinPrice().equals(BigDecimal.ZERO))
                    .map(TradeGroup::getMinPrice)
                    .min(Comparator.comparing(Function.identity())).orElse(null);

            BigDecimal maxPrice = tradeGroupStream(tradeFrames)
                    .filter(tg -> tg.getMaxPrice() != null && !tg.getMaxPrice().equals(BigDecimal.ZERO))
                    .map(TradeGroup::getMaxPrice)
                    .max(Comparator.comparing(Function.identity())).orElse(null);


            if (minPrice == null || maxPrice == null) {
                throw new TradesNotFoundException("No trades inside tradeframes");
            }

            return definePriceGrid(minPrice, maxPrice);
        } else {
            return DEFAULT_EMPTY_GRID;
        }
    }

    public static Stream<TradeGroup> tradeGroupStream(List<TradeFrame> tradeFrames) {
        return Stream.concat(
                tradeFrames.stream().filter(tf -> tf.getBuyTradeGroup() != null).map(TradeFrame::getBuyTradeGroup),
                tradeFrames.stream().filter(tf -> tf.getSellTradeGroup() != null).map(TradeFrame::getSellTradeGroup));
    }


    public static PriceGridDto definePriceGrid(BigDecimal minPrice, BigDecimal maxPrice) {
        BigDecimal delta = maxPrice.subtract(minPrice);
//        if (delta.scale() >= 1) {
//            delta = delta.setScale(delta.scale() - 1, RoundingMode.UP);
//        }
//        if (minPrice.scale() >= 1) {
//            minPrice = minPrice.setScale(minPrice.scale() - 1, RoundingMode.DOWN);
//        }
        BigDecimal shift = delta.divide(new BigDecimal(STEPS), delta.scale() + 2, RoundingMode.CEILING);

        List<BigDecimal> priceGrid = new ArrayList<>();

        for (int i = 0; i <= STEPS; i++) {
            priceGrid.add(
                    minPrice.add(shift.multiply(new BigDecimal(i)))
            );

        }
        return new PriceGridDto(minPrice, maxPrice, priceGrid);
    }

}
