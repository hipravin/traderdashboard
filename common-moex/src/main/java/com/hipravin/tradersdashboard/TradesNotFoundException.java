package com.hipravin.tradersdashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradesNotFoundException extends RuntimeException{

    public TradesNotFoundException() {
    }

    public TradesNotFoundException(String emcode, LocalDate day) {
        this("No trades found for: " + emcode + " at " + day.format(DateTimeFormatter.ISO_DATE));
    }

    public TradesNotFoundException(String message) {
        super(message);
    }

    public TradesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradesNotFoundException(Throwable cause) {
        super(cause);
    }

    public TradesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
