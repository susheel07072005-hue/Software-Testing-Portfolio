package com.msrit.travel.model;

/** States of the booking lifecycle (see 02_Test_Case_Design/04_State_Transition.md). */
public enum BookingStatus {
    CREATED,
    PAYMENT_PENDING,
    PAYMENT_FAILED,
    CONFIRMED,
    CANCELLED,
    EXPIRED,
    COMPLETED
}
