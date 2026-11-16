package com.msrit.travel.service;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Validates user input for registration and booking.
 *
 * Rules (from the test plan):
 *  - Travellers per booking: 1 to 10 (inclusive)
 *  - Password: 8 to 16 characters, at least one letter and one digit
 *  - Email: name@domain.tld
 *  - Phone: 10-digit Indian mobile number starting with 6, 7, 8 or 9
 *  - Travel date: from tomorrow up to 365 days from today (inclusive)
 */
public class InputValidator {

    public static final int MIN_TRAVELLERS = 1;
    public static final int MAX_TRAVELLERS = 10;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int MAX_ADVANCE_BOOKING_DAYS = 365;

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile("^[6-9]\\d{9}$");

    public boolean isValidTravellerCount(int count) {
        return count >= MIN_TRAVELLERS && count <= MAX_TRAVELLERS;
    }

    public boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public boolean isValidPhone(String phone) {
        return phone != null && PHONE.matcher(phone).matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        int len = password.length();
        if (len < MIN_PASSWORD_LENGTH || len > MAX_PASSWORD_LENGTH) {
            return false;
        }
        boolean hasLetter = password.chars().anyMatch(Character::isLetter);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasLetter && hasDigit;
    }

    public boolean isValidTravelDate(LocalDate travelDate, LocalDate today) {
        if (travelDate == null || today == null) {
            return false;
        }
        return travelDate.isAfter(today)
                && !travelDate.isAfter(today.plusDays(MAX_ADVANCE_BOOKING_DAYS));
    }
}
