package com.msrit.travel;

import com.msrit.travel.service.PriceCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Decision table for discounts. See 02_Test_Case_Design/03_Decision_Table.md
 *
 * C1 Group (5+ travellers)   C2 Early bird (30+ days)   C3 Valid coupon   -> Discount %
 */
@DisplayName("Decision Table - Discounts")
class DiscountDecisionTableTest {

    private final PriceCalculator price = new PriceCalculator();

    @ParameterizedTest(name = "{0}")
    @CsvSource(value = {
            "'TC-DT-01: Rule 1 - no group, no early bird, no coupon -> 0%',  2, 10, NULL,   0",
            "'TC-DT-02: Rule 2 - coupon only -> 10%',                         2, 10, TOUR10, 10",
            "'TC-DT-03: Rule 3 - early bird only -> 5%',                      2, 45, NULL,   5",
            "'TC-DT-04: Rule 4 - early bird + coupon -> 15%',                 2, 45, TOUR10, 15",
            "'TC-DT-05: Rule 5 - group only -> 10%',                          6, 10, NULL,   10",
            "'TC-DT-06: Rule 6 - group + coupon -> 20%',                      6, 10, TOUR10, 20",
            "'TC-DT-07: Rule 7 - group + early bird -> 15%',                  6, 45, NULL,   15",
            "'TC-DT-08: Rule 8 - all three (25%) capped -> 20%',              6, 45, TOUR10, 20"
    }, nullValues = "NULL")
    void discountRules(String id, int travellers, long daysInAdvance, String coupon, int expected) {
        assertEquals(expected, price.discountPercent(travellers, daysInAdvance, coupon));
    }
}
