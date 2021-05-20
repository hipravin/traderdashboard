package com.hipravin.traderdashboard.api;

import com.hipravin.traderdashboard.api.dto.TradeAggregationDto;
import com.hipravin.tradersdashboard.MoexFileStorage;
import com.hipravin.tradersdashboard.moex.model.Trade;
import com.hipravin.tradersdashboard.moex.model.TradeFrame;
import com.hipravin.tradersdashboard.utils.TradeFrameUtil;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
public class TradeFrameController {
    private static final DateTimeFormatter DATE_PARAM_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final MoexFileStorage moexFileStorage;

    public TradeFrameController(MoexFileStorage moexFileStorage) {
        this.moexFileStorage = moexFileStorage;
    }

    @GetMapping("/aggregation-daily/{emcode}/{day}")
    public ResponseEntity<?> aggregationDaily(@PathVariable("emcode") String emCode,
                                              @PathVariable("day") String dayString,
                                              @RequestParam(value = "frame-size", defaultValue = "6300") Long frameSize) throws IOException {
        Assert.notNull(emCode, "emcode is required");
        Assert.notNull(dayString, "day is required");
        Assert.notNull(frameSize, "frameSize is required");

        //TODO: separate this logic to service and consider preloading of frequently used

        LocalDate day = LocalDate.from(DATE_PARAM_FORMAT.parse(dayString));
        Stream<Trade> trades = moexFileStorage.findTrades(emCode, day);

        LocalDateTime start = day.atTime(9, 50);
        LocalDateTime end = day.atTime(23, 59);

        List<TradeFrame> frames = TradeFrameUtil.mergeTradesToTradeFrames(trades, start, end, Duration.ofMillis(frameSize));

        TradeAggregationDto result = TradeAggregationDto.of(emCode.toUpperCase(), start, end, frameSize, frames);

        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParse(DateTimeParseException e) {
        return new ResponseEntity<Object>("date has wrong format: " + e.getParsedString(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleDateTimeParse(IOException e) {
        return new ResponseEntity<Object>("I/O error: " + e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
