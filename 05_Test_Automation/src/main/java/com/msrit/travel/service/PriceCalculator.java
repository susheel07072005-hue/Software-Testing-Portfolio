package com.msrit.travel.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Calculates the total booking price.
 *
 * Fare by age:   Infant (0-1) = free, Child (2-11) = 50%, Adult (12-59) = 100%, Senior (60+) = 80%
 * Discounts:     Group of 5+ travellers = 10%, Early bird (booked 30+ days ahead) = 5%,
 *                Coupon "TOUR10" = 10%.  Discounts add up but are capped at 20%.
 * Tax:           5% GST on the discounted amount.
 */
public class PriceCalculator {

    public enum FareCategory { INFANT, CHILD, ADULT, SENIOR }

    public static final String VALID_COUPON = "TOUR10";
    public static final int GROUP_SIZE = 5;
    public static final int EARLY_BIRD_DAYS = 30;
    public static final int MAX_DISCOUNT_PERCENT = 20;
    public static final BigDecimal GST_RATE = new BigDecimal("0.05");

    public FareCategory categorize(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        if (age < 2) return FareCategory.INFANT;
        if (age <= 12) return FareCategory.CHILD;
        if (age < 60) return FareCategory.ADULT;
        return FareCategory.SENIOR;
    }

    public BigDecimal fareFor(BigDecimal adultPrice, int age) {
        return switch (categorize(age)) {
            case INFANT -> BigDecimal.ZERO;
            case CHILD -> adultPrice.multiply(new BigDecimal("0.50"));
            case ADULT -> adultPrice;
            case SENIOR -> adultPrice.multiply(new BigDecimal("0.80"));
        };
    }

    public int discountPercent(int travellerCount, long daysInAdvance, String coupon) {
        int discount = 0;
        if (travellerCount >= GROUP_SIZE) discount += 10;
        if (daysInAdvance >= EARLY_BIRD_DAYS) discount += 5;
        if (coupon != null && VALID_COUPON.equalsIgnoreCase(coupon.trim())) discount += 10;
        return Math.min(discount, MAX_DISCOUNT_PERCENT);
    }

    public BigDecimal totalPrice(BigDecimal adultPrice, List<Integer> ages,
                                 LocalDate bookingDate, LocalDate travelDate, String coupon) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (int age : ages) {
            subtotal = subtotal.add(fareFor(adultPrice, age));
        }
        long daysInAdvance = ChronoUnit.DAYS.between(bookingDate, travelDate);
        int discount = discountPercent(ages.size(), daysInAdvance, coupon);

        BigDecimal afterDiscount = subtotal.multiply(BigDecimal.valueOf(100 - discount))
                .divide(BigDecimal.valueOf(100));
        BigDecimal withTax = afterDiscount.add(afterDiscount.multiply(GST_RATE));
        return withTax.setScale(2, RoundingMode.HALF_UP);
    }
}
