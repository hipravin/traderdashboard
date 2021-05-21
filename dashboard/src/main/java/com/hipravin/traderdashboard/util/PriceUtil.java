package com.hipravin.traderdashboard.util;

import com.hipravin.traderdashboard.api.dto.PriceGridDto;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.moex.model.TradeGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class PriceUtil {

    private static final int STEPS = 20;

    private static final PriceGridDto DEFAULT_EMPTY_GRID = new PriceGridDto(BigDecimal.ONE, BigDecimal.TEN, Collections.emptyList());

    private PriceUtil() {
    }

    public static PriceGridDto definePriceGrid(List<TradeFrame> tradeFrames) {
        if (!tradeFrames.isEmpty()) {
            BigDecimal minPrice = tradeGroupStream(tradeFrames)
                    .filter(tg -> tg.getMinPrice() != null && !tg.getMinPrice().equals(BigDecimal.ZERO))
                    .map(TradeGroup::getMinPrice)
                    .min(Comparator.comparing(Function.identity())).orElseThrow();

            BigDecimal maxPrice = tradeGroupStream(tradeFrames)
                    .filter(tg -> tg.getMaxPrice() != null && !tg.getMaxPrice().equals(BigDecimal.ZERO))
                    .map(TradeGroup::getMaxPrice)
                    .max(Comparator.comparing(Function.identity())).orElseThrow();

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
        if (delta.scale() >= 2) {
            delta = delta.setScale(delta.scale() - 2, RoundingMode.UP);
        }
        if (minPrice.scale() >= 2) {
            minPrice = minPrice.setScale(minPrice.scale() - 2, RoundingMode.DOWN);
        }
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
