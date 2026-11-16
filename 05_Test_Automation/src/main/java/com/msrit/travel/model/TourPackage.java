package com.msrit.travel.model;

import java.math.BigDecimal;

/** A tour package offered by the travel agency. Seat count changes as bookings are confirmed/cancelled. */
public class TourPackage {

    private final String id;
    private final String destination;
    private final BigDecimal pricePerAdult;
    private final int durationDays;
    private int seatsAvailable;

    public TourPackage(String id, String destination, BigDecimal pricePerAdult, int durationDays, int seatsAvailable) {
        if (pricePerAdult == null || pricePerAdult.signum() < 0) {
            throw new IllegalArgumentException("Price must be zero or positive");
        }
        if (durationDays < 1) {
            throw new IllegalArgumentException("Duration must be at least 1 day");
        }
        if (seatsAvailable < 0) {
            throw new IllegalArgumentException("Seats cannot be negative");
        }
        this.id = id;
        this.destination = destination;
        this.pricePerAdult = pricePerAdult;
        this.durationDays = durationDays;
        this.seatsAvailable = seatsAvailable;
    }

    public void reserveSeats(int count) {
        if (count > seatsAvailable) {
            throw new IllegalStateException("Insufficient seats: requested " + count + ", available " + seatsAvailable);
        }
        seatsAvailable -= count;
    }

    public void releaseSeats(int count) {
        seatsAvailable += count;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }
    public BigDecimal getPricePerAdult() { return pricePerAdult; }
    public int getDurationDays() { return durationDays; }
    public int getSeatsAvailable() { return seatsAvailable; }
}
