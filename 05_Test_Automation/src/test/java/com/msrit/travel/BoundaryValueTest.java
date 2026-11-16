package com.msrit.travel;

import com.msrit.travel.service.InputValidator;
import com.msrit.travel.service.PriceCalculator;
import com.msrit.travel.service.PriceCalculator.FareCategory;
import com.msrit.travel.service.RefundCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.msrit.travel.TestData.TODAY;
import static org.junit.jupiter.api.Assertions.*;

/** Boundary Value Analysis test cases. See 02_Test_Case_Design/02_Boundary_Value_Analysis.md */
@DisplayName("Boundary Value Analysis")
class BoundaryValueTest {

    private final InputValidator validator = new InputValidator();
    private final PriceCalculator price = new PriceCalculator();
    private final RefundCalculator refund = new RefundCalculator();

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-BVA-01: Travellers = 0 (min-1)', 0, false",
            "'TC-BVA-02: Travellers = 1 (min)', 1, true",
            "'TC-BVA-03: Travellers = 2 (min+1)', 2, true",
            "'TC-BVA-04: Travellers = 9 (max-1)', 9, true",
            "'TC-BVA-05: Travellers = 10 (max)', 10, true",
            "'TC-BVA-06: Travellers = 11 (max+1)', 11, false"
    })
    void travellerCount(String id, int count, boolean expected) {
        assertEquals(expected, validator.isValidTravellerCount(count));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-BVA-07: Password length 7 (min-1)', 7, false",
            "'TC-BVA-08: Password length 8 (min)', 8, true",
            "'TC-BVA-09: Password length 9 (min+1)', 9, true",
            "'TC-BVA-10: Password length 15 (max-1)', 15, true",
            "'TC-BVA-11: Password length 16 (max)', 16, true",
            "'TC-BVA-12: Password length 17 (max+1)', 17, false"
    })
    void passwordLength(String id, int length, boolean expected) {
        String password = "a1" + "x".repeat(length - 2);
        assertEquals(length, password.length());
        assertEquals(expected, validator.isValidPassword(password));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-BVA-13: Age 1 is INFANT', 1, INFANT",
            "'TC-BVA-14: Age 2 is CHILD', 2, CHILD",
            "'TC-BVA-15: Age 11 is CHILD', 11, CHILD",
            "'TC-BVA-16: Age 12 is ADULT', 12, ADULT",
            "'TC-BVA-17: Age 13 is ADULT', 13, ADULT",
            "'TC-BVA-18: Age 59 is ADULT', 59, ADULT",
            "'TC-BVA-19: Age 60 is SENIOR', 60, SENIOR",
            "'TC-BVA-20: Age 61 is SENIOR', 61, SENIOR"
    })
    void fareCategoryByAge(String id, int age, FareCategory expected) {
        assertEquals(expected, price.categorize(age));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-BVA-21: Travel date = today (min-1)', 0, false",
            "'TC-BVA-22: Travel date = today+1 (min)', 1, true",
            "'TC-BVA-23: Travel date = today+2 (min+1)', 2, true",
            "'TC-BVA-24: Travel date = today+364 (max-1)', 364, true",
            "'TC-BVA-25: Travel date = today+365 (max)', 365, true",
            "'TC-BVA-26: Travel date = today+366 (max+1)', 366, false"
    })
    void travelDateWindow(String id, int daysAhead, boolean expected) {
        assertEquals(expected, validator.isValidTravelDate(TODAY.plusDays(daysAhead), TODAY));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-BVA-27: Cancel 31 days before departure -> 90%', 31, 90",
            "'TC-BVA-28: Cancel 30 days before departure -> 50%', 30, 50",
            "'TC-BVA-29: Cancel 16 days before departure -> 50%', 16, 50",
            "'TC-BVA-30: Cancel 15 days before departure -> 50%', 15, 50",
            "'TC-BVA-31: Cancel 14 days before departure -> 25%', 14, 25",
            "'TC-BVA-32: Cancel 8 days before departure -> 25%', 8, 25",
            "'TC-BVA-33: Cancel 7 days before departure -> 25%', 7, 25",
            "'TC-BVA-34: Cancel 6 days before departure -> 0%', 6, 0",
            "'TC-BVA-35: Cancel on departure day -> 0%', 0, 0"
    })
    void refundSlabs(String id, int daysBefore, int expectedPercent) {
        assertEquals(expectedPercent, refund.refundPercent(TODAY, TODAY.plusDays(daysBefore)));
    }

    @Test
    @DisplayName("TC-BVA-36: Cancel 1 day after departure is rejected")
    void cancelAfterDeparture() {
        assertThrows(IllegalArgumentException.class, () -> refund.refundPercent(TODAY, TODAY.minusDays(1)));
    }
}
