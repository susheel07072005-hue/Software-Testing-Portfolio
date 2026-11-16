package com.msrit.travel.service;

import com.msrit.travel.model.TourPackage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/** Search and filter tour packages by destination, budget and duration. Null filters are ignored. */
public class PackageSearch {

    public List<TourPackage> search(List<TourPackage> packages, String destination,
                                    BigDecimal maxBudget, Integer minDays, Integer maxDays) {
        if (maxBudget != null && maxBudget.signum() < 0) {
            throw new IllegalArgumentException("Budget cannot be negative");
        }
        if (minDays != null && maxDays != null && minDays > maxDays) {
            throw new IllegalArgumentException("Minimum days cannot exceed maximum days");
        }
        String query = destination == null ? "" : destination.trim().toLowerCase(Locale.ROOT);

        return packages.stream()
                .filter(p -> query.isEmpty() || p.getDestination().toLowerCase(Locale.ROOT).contains(query))
                .filter(p -> maxBudget == null || p.getPricePerAdult().compareTo(maxBudget) <= 0)
                .filter(p -> minDays == null || p.getDurationDays() >= minDays)
                .filter(p -> maxDays == null || p.getDurationDays() <= maxDays)
                .toList();
    }
}
