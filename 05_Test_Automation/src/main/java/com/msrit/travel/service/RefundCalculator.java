package com.msrit.travel.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Refund policy based on days between cancellation and departure:
 *   more than 30 days -> 90%
 *   15 to 30 days     -> 50%
 *   7 to 14 days      -> 25%
 *   0 to 6 days       -> 0%
 *   after departure   -> not allowed
 */
public class RefundCalculator {

    public int refundPercent(LocalDate cancellationDate, LocalDate departureDate) {
        long days = ChronoUnit.DAYS.between(cancellationDate, departureDate);
        if (days < 0) {
            throw new IllegalArgumentException("Cannot cancel after departure");
        }
        if (days > 30) return 90;
        if (days > 15) return 50;
        if (days >= 7) return 25;
        return 0;
    }

    public BigDecimal refundAmount(BigDecimal amountPaid, LocalDate cancellationDate, LocalDate departureDate) {
        int percent = refundPercent(cancellationDate, departureDate);
        return amountPaid.multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
