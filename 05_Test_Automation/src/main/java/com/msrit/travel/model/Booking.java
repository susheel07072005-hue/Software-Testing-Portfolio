package com.msrit.travel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** A booking of one tour package for a group of travellers. */
public class Booking {

    private final String id;
    private final TourPackage tourPackage;
    private final List<Traveller> travellers;
    private final LocalDate travelDate;
    private final BigDecimal amount;
    private BookingStatus status = BookingStatus.CREATED;
    private int failedPaymentAttempts = 0;

    public Booking(String id, TourPackage tourPackage, List<Traveller> travellers, LocalDate travelDate, BigDecimal amount) {
        this.id = id;
        this.tourPackage = tourPackage;
        this.travellers = List.copyOf(travellers);
        this.travelDate = travelDate;
        this.amount = amount;
    }

    public String getId() { return id; }
    public TourPackage getTourPackage() { return tourPackage; }
    public List<Traveller> getTravellers() { return travellers; }
    public int getTravellerCount() { return travellers.size(); }
    public LocalDate getTravelDate() { return travelDate; }
    public BigDecimal getAmount() { return amount; }
    public BookingStatus getStatus() { return status; }
    public int getFailedPaymentAttempts() { return failedPaymentAttempts; }

    public void setStatus(BookingStatus status) { this.status = status; }
    public void incrementFailedPaymentAttempts() { failedPaymentAttempts++; }
}
