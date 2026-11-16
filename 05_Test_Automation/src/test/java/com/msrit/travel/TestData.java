package com.msrit.travel;

import com.msrit.travel.model.TourPackage;
import com.msrit.travel.model.Traveller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Shared, fixed test data so every run is repeatable. */
public final class TestData {

    /** Fixed "today" so date-based tests never depend on the real clock. */
    public static final LocalDate TODAY = LocalDate.of(2026, 12, 1);

    private TestData() {
    }

    public static TourPackage goaPackage(int seats) {
        return new TourPackage("PKG-GOA-01", "Goa Beach Escape", new BigDecimal("5000"), 4, seats);
    }

    public static List<TourPackage> catalogue() {
        return List.of(
                new TourPackage("PKG-GOA-01", "Goa Beach Escape", new BigDecimal("5000"), 4, 20),
                new TourPackage("PKG-MYS-02", "Mysore Heritage Tour", new BigDecimal("3500"), 2, 30),
                new TourPackage("PKG-KSH-03", "Kashmir Valley Trip", new BigDecimal("18000"), 7, 15),
                new TourPackage("PKG-KER-04", "Kerala Backwaters", new BigDecimal("12000"), 5, 25),
                new TourPackage("PKG-GOA-05", "North Goa Adventure", new BigDecimal("7500"), 3, 10));
    }

    public static List<Traveller> adults(int count) {
        List<Traveller> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            list.add(new Traveller("Traveller " + i, 30));
        }
        return list;
    }
}
