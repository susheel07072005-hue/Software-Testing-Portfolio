package com.msrit.travel;

import com.msrit.travel.model.Booking;
import com.msrit.travel.model.Traveller;
import com.msrit.travel.service.BookingService;
import com.msrit.travel.service.PriceCalculator;
import com.msrit.travel.service.RefundCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.msrit.travel.TestData.TODAY;
import static org.junit.jupiter.api.Assertions.*;

/** End-to-end functional checks on price, refund and booking rules. */
@DisplayName("Functional - Booking and Pricing")
class BookingAndPricingTest {

    private final PriceCalculator price = new PriceCalculator();
    private final RefundCalculator refund = new RefundCalculator();
    private final BookingService service = new BookingService();

    @Test
    @DisplayName("TC-FN-01: 2 adults + 1 child, Rs 5000 package, no discount -> Rs 13125.00 incl. GST")
    void simpleFamilyPrice() {
        // 5000 + 5000 + 2500 = 12500, no discount, +5% GST = 13125.00
        BigDecimal total = price.totalPrice(new BigDecimal("5000"), List.of(35, 33, 8),
                TODAY, TODAY.plusDays(10), null);
        assertEquals(new BigDecimal("13125.00"), total);
    }

    @Test
    @DisplayName("TC-FN-02: Group of 5 (all fare types), early bird + coupon, Rs 10000 package -> Rs 27720.00")
    void groupPriceWithCappedDiscount() {
        // 10000 + 10000 + 5000 (child 8) + 0 (infant 1) + 8000 (senior 65) = 33000
        // discount 10 + 5 + 10 = 25 -> capped at 20 -> 26400, +5% GST = 27720.00
        BigDecimal total = price.totalPrice(new BigDecimal("10000"), List.of(35, 33, 8, 1, 65),
                TODAY, TODAY.plusDays(45), "TOUR10");
        assertEquals(new BigDecimal("27720.00"), total);
    }

    @Test
    @DisplayName("TC-FN-03: Coupon ' tour10 ' (lowercase with spaces) is accepted")
    void couponIsCaseAndSpaceInsensitive() {
        assertEquals(10, price.discountPercent(2, 10, " tour10 "));
    }

    @Test
    @DisplayName("TC-FN-04: Unknown coupon TOUR50 gives no discount")
    void invalidCouponGivesNoDiscount() {
        assertEquals(0, price.discountPercent(2, 10, "TOUR50"));
    }

    @Test
    @DisplayName("TC-FN-05: Booking 4 travellers when only 3 seats left is rejected")
    void insufficientSeats() {
        assertThrows(IllegalStateException.class, () -> service.createBooking(
                TestData.goaPackage(3), TestData.adults(4), TODAY.plusDays(20), TODAY, null));
    }

    @Test
    @DisplayName("TC-FN-06: Refund on Rs 27720 paid, cancelled 20 days before -> Rs 13860.00")
    void refundAmount() {
        assertEquals(new BigDecimal("13860.00"),
                refund.refundAmount(new BigDecimal("27720"), TODAY, TODAY.plusDays(20)));
    }

    @Test
    @DisplayName("TC-FN-07: Negative traveller age is rejected")
    void negativeAgeRejected() {
        assertThrows(IllegalArgumentException.class, () -> price.categorize(-1));
    }

    @Test
    @DisplayName("TC-FN-08: Booking with 11 travellers is rejected")
    void tooManyTravellers() {
        assertThrows(IllegalArgumentException.class, () -> service.createBooking(
                TestData.goaPackage(50), TestData.adults(11), TODAY.plusDays(20), TODAY, null));
    }

    @Test
    @DisplayName("TC-FN-09: Valid booking gets an ID and the correct amount")
    void validBookingCreated() {
        List<Traveller> travellers = List.of(new Traveller("Asha", 30), new Traveller("Ravi", 62));
        Booking booking = service.createBooking(TestData.goaPackage(20), travellers, TODAY.plusDays(40), TODAY, null);
        // 5000 + 4000 = 9000, early bird 5% -> 8550, +5% GST = 8977.50
        assertNotNull(booking.getId());
        assertEquals(new BigDecimal("8977.50"), booking.getAmount());
    }
}
