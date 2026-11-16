package com.msrit.travel.service;

import com.msrit.travel.model.Booking;
import com.msrit.travel.model.BookingStatus;
import com.msrit.travel.model.TourPackage;
import com.msrit.travel.model.Traveller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates bookings and moves them through the booking lifecycle.
 *
 *   CREATED --initiatePayment--> PAYMENT_PENDING --paymentSuccess--> CONFIRMED --complete--> COMPLETED
 *                                     |  ^
 *                       paymentFailure|  |initiatePayment (retry)
 *                                     v  |
 *                                PAYMENT_FAILED  (3rd failure -> EXPIRED)
 *
 *   cancel is allowed from CREATED, PAYMENT_PENDING, PAYMENT_FAILED and CONFIRMED.
 */
public class BookingService {

    public static final int MAX_PAYMENT_ATTEMPTS = 3;

    private static final Set<BookingStatus> CANCELLABLE = EnumSet.of(
            BookingStatus.CREATED, BookingStatus.PAYMENT_PENDING,
            BookingStatus.PAYMENT_FAILED, BookingStatus.CONFIRMED);

    private final InputValidator validator = new InputValidator();
    private final PriceCalculator priceCalculator = new PriceCalculator();
    private final AtomicInteger sequence = new AtomicInteger(1000);

    public Booking createBooking(TourPackage pkg, List<Traveller> travellers,
                                 LocalDate travelDate, LocalDate today, String coupon) {
        if (!validator.isValidTravellerCount(travellers.size())) {
            throw new IllegalArgumentException("Travellers must be between "
                    + InputValidator.MIN_TRAVELLERS + " and " + InputValidator.MAX_TRAVELLERS);
        }
        if (!validator.isValidTravelDate(travelDate, today)) {
            throw new IllegalArgumentException("Travel date must be between tomorrow and 365 days from today");
        }
        if (travellers.size() > pkg.getSeatsAvailable()) {
            throw new IllegalStateException("Insufficient seats");
        }
        List<Integer> ages = travellers.stream().map(Traveller::age).toList();
        BigDecimal amount = priceCalculator.totalPrice(pkg.getPricePerAdult(), ages, today, travelDate, coupon);
        return new Booking("BK" + sequence.incrementAndGet(), pkg, travellers, travelDate, amount);
    }

    public void initiatePayment(Booking booking) {
        requireStatus(booking, EnumSet.of(BookingStatus.CREATED, BookingStatus.PAYMENT_FAILED), "initiate payment");
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
    }

    public void paymentSuccess(Booking booking) {
        requireStatus(booking, EnumSet.of(BookingStatus.PAYMENT_PENDING), "confirm payment");
        booking.getTourPackage().reserveSeats(booking.getTravellerCount());
        booking.setStatus(BookingStatus.CONFIRMED);
    }

    public void paymentFailure(Booking booking) {
        requireStatus(booking, EnumSet.of(BookingStatus.PAYMENT_PENDING), "record payment failure");
        booking.incrementFailedPaymentAttempts();
        if (booking.getFailedPaymentAttempts() > MAX_PAYMENT_ATTEMPTS) {
            booking.setStatus(BookingStatus.EXPIRED);
        } else {
            booking.setStatus(BookingStatus.PAYMENT_FAILED);
        }
    }

    public void cancel(Booking booking) {
        requireStatus(booking, CANCELLABLE, "cancel");
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            booking.getTourPackage().releaseSeats(booking.getTravellerCount());
        }
        booking.setStatus(BookingStatus.CANCELLED);
    }

    public void complete(Booking booking) {
        requireStatus(booking, EnumSet.of(BookingStatus.CONFIRMED), "complete");
        booking.setStatus(BookingStatus.COMPLETED);
    }

    private void requireStatus(Booking booking, Set<BookingStatus> allowed, String action) {
        if (!allowed.contains(booking.getStatus())) {
            throw new IllegalStateException("Cannot " + action + " when booking is " + booking.getStatus());
        }
    }
}
