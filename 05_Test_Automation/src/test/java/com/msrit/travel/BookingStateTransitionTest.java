package com.msrit.travel;

import com.msrit.travel.model.Booking;
import com.msrit.travel.model.BookingStatus;
import com.msrit.travel.model.TourPackage;
import com.msrit.travel.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.msrit.travel.TestData.TODAY;
import static org.junit.jupiter.api.Assertions.*;

/** State transition test cases for the booking lifecycle. See 02_Test_Case_Design/04_State_Transition.md */
@DisplayName("State Transition - Booking Lifecycle")
class BookingStateTransitionTest {

    private BookingService service;
    private TourPackage pkg;
    private Booking booking;

    @BeforeEach
    void setUp() {
        service = new BookingService();
        pkg = TestData.goaPackage(20);
        booking = service.createBooking(pkg, TestData.adults(2), TODAY.plusDays(20), TODAY, null);
    }

    // ---------- Valid transitions ----------

    @Test
    @DisplayName("TC-ST-01: New booking starts in CREATED")
    void newBookingIsCreated() {
        assertEquals(BookingStatus.CREATED, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-02: CREATED -> PAYMENT_PENDING on initiatePayment")
    void createdToPending() {
        service.initiatePayment(booking);
        assertEquals(BookingStatus.PAYMENT_PENDING, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-03: PAYMENT_PENDING -> CONFIRMED on paymentSuccess and seats are reserved")
    void pendingToConfirmed() {
        service.initiatePayment(booking);
        service.paymentSuccess(booking);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(18, pkg.getSeatsAvailable());
    }

    @Test
    @DisplayName("TC-ST-04: PAYMENT_PENDING -> PAYMENT_FAILED on first failure")
    void pendingToFailed() {
        service.initiatePayment(booking);
        service.paymentFailure(booking);
        assertEquals(BookingStatus.PAYMENT_FAILED, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-05: PAYMENT_FAILED -> PAYMENT_PENDING on retry")
    void failedToPendingRetry() {
        service.initiatePayment(booking);
        service.paymentFailure(booking);
        service.initiatePayment(booking);
        assertEquals(BookingStatus.PAYMENT_PENDING, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-06: Third payment failure -> EXPIRED")
    void thirdFailureExpires() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            service.initiatePayment(booking);
            service.paymentFailure(booking);
        }
        assertEquals(BookingStatus.EXPIRED, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-07: CONFIRMED -> COMPLETED after the trip")
    void confirmedToCompleted() {
        service.initiatePayment(booking);
        service.paymentSuccess(booking);
        service.complete(booking);
        assertEquals(BookingStatus.COMPLETED, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-08: CONFIRMED -> CANCELLED releases reserved seats")
    void confirmedToCancelledReleasesSeats() {
        service.initiatePayment(booking);
        service.paymentSuccess(booking);
        service.cancel(booking);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        assertEquals(20, pkg.getSeatsAvailable());
    }

    @Test
    @DisplayName("TC-ST-13: PAYMENT_PENDING -> CANCELLED (seats were never reserved)")
    void pendingToCancelled() {
        service.initiatePayment(booking);
        service.cancel(booking);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        assertEquals(20, pkg.getSeatsAvailable());
    }

    @Test
    @DisplayName("TC-ST-14: PAYMENT_FAILED -> CANCELLED")
    void failedToCancelled() {
        service.initiatePayment(booking);
        service.paymentFailure(booking);
        service.cancel(booking);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }

    // ---------- Invalid transitions (must be rejected) ----------

    @Test
    @DisplayName("TC-ST-09: Cancel a COMPLETED booking is rejected")
    void cannotCancelCompleted() {
        service.initiatePayment(booking);
        service.paymentSuccess(booking);
        service.complete(booking);
        assertThrows(IllegalStateException.class, () -> service.cancel(booking));
        assertEquals(BookingStatus.COMPLETED, booking.getStatus());
    }

    @Test
    @DisplayName("TC-ST-10: paymentSuccess on a CREATED booking is rejected")
    void cannotConfirmWithoutPayment() {
        assertThrows(IllegalStateException.class, () -> service.paymentSuccess(booking));
    }

    @Test
    @DisplayName("TC-ST-11: Retry payment on an EXPIRED booking is rejected")
    void cannotRetryExpired() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            service.initiatePayment(booking);
            service.paymentFailure(booking);
        }
        assertThrows(IllegalStateException.class, () -> service.initiatePayment(booking));
    }

    @Test
    @DisplayName("TC-ST-12: Complete a CANCELLED booking is rejected")
    void cannotCompleteCancelled() {
        service.cancel(booking);
        assertThrows(IllegalStateException.class, () -> service.complete(booking));
    }
}
