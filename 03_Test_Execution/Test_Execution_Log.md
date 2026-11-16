# Test Execution Log

**Application:** Travel and Tour Package Booking System  
**Build under test:** Cycle 1 = v1.0 (first build), Cycle 2 = v2.0 (after defect fixes)  
**Execution method:** Automated with JUnit 5 (`05_Test_Automation`), one row per test case / parameterized input  
**Environment:** Java 17+, JUnit 5.10, Maven, IntelliJ IDEA  
**Executed by:** Shreyas  **Date:** 16-11-2026

## Summary

| Cycle | Build | Executed | Passed | Failed | Pass % |
|---|---|---|---|---|---|
| 1 | v1.0 | 87 | 77 | 10 | 88.5% |
| 2 | v2.0 | 87 | 87 | 0 | 100.0% |

The same file is available as `test_execution.csv` (opens in Excel).

## Detailed log

### Equivalence Partitioning

| TC ID | Module | Test case (input -> expected) | Cycle 1 actual result | Cycle 1 | Cycle 2 | Defect |
|---|---|---|---|---|---|---|
| TC-EP-01 | Booking | Traveller count 5 (valid class 1-10) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-02 | Booking | Traveller count -3 (invalid class < 1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-03 | Booking | Traveller count 15 (invalid class > 10) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-04 | Registration | Email shreyas@gmail.com (valid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-05 | Registration | Email without @ (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-06 | Registration | Email without domain extension (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-07 | Registration | Empty email (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-08 | Registration | Null email (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-09 | Registration | Phone 9876543210 (valid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-10 | Registration | Phone starting with 5 (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-11 | Registration | Phone with 5 digits (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-12 | Registration | Phone with letters (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-13 | Registration | Password with letters and digits (valid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-14 | Registration | Password with no digit (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-15 | Registration | Password with no letter (invalid) | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-16 | Package Search | Search 'Goa' returns both Goa packages | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-17 | Package Search | Search 'goa' (lowercase) returns both Goa packages | expected: &lt;2&gt; but was: &lt;0&gt; | ❌ FAIL | ✅ PASS | DEF-08 |
| TC-EP-18 | Package Search | Search 'Paris' (no such package) returns empty list | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-19 | Package Search | Negative budget is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-EP-20 | Package Search | Blank destination with budget 8000 returns packages priced <= 8000 | As expected | ✅ PASS | ✅ PASS | - |

### Boundary Value Analysis

| TC ID | Module | Test case (input -> expected) | Cycle 1 actual result | Cycle 1 | Cycle 2 | Defect |
|---|---|---|---|---|---|---|
| TC-BVA-01 | Booking | Travellers = 0 (min-1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-02 | Booking | Travellers = 1 (min) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-03 | Booking | Travellers = 2 (min+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-04 | Booking | Travellers = 9 (max-1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-05 | Booking | Travellers = 10 (max) | expected: &lt;true&gt; but was: &lt;false&gt; | ❌ FAIL | ✅ PASS | DEF-06 |
| TC-BVA-06 | Booking | Travellers = 11 (max+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-07 | Registration | Password length 7 (min-1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-08 | Registration | Password length 8 (min) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-09 | Registration | Password length 9 (min+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-10 | Registration | Password length 15 (max-1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-11 | Registration | Password length 16 (max) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-12 | Registration | Password length 17 (max+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-13 | Pricing & Discounts | Age 1 is INFANT | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-14 | Pricing & Discounts | Age 2 is CHILD | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-15 | Pricing & Discounts | Age 11 is CHILD | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-16 | Pricing & Discounts | Age 12 is ADULT | expected: &lt;ADULT&gt; but was: &lt;CHILD&gt; | ❌ FAIL | ✅ PASS | DEF-05 |
| TC-BVA-17 | Pricing & Discounts | Age 13 is ADULT | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-18 | Pricing & Discounts | Age 59 is ADULT | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-19 | Pricing & Discounts | Age 60 is SENIOR | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-20 | Pricing & Discounts | Age 61 is SENIOR | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-21 | Booking | Travel date = today (min-1) | expected: &lt;false&gt; but was: &lt;true&gt; | ❌ FAIL | ✅ PASS | DEF-07 |
| TC-BVA-22 | Booking | Travel date = today+1 (min) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-23 | Booking | Travel date = today+2 (min+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-24 | Booking | Travel date = today+364 (max-1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-25 | Booking | Travel date = today+365 (max) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-26 | Booking | Travel date = today+366 (max+1) | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-27 | Cancellation & Refund | Cancel 31 days before departure → 90% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-28 | Cancellation & Refund | Cancel 30 days before departure → 50% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-29 | Cancellation & Refund | Cancel 16 days before departure → 50% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-30 | Cancellation & Refund | Cancel 15 days before departure → 50% | expected: &lt;50&gt; but was: &lt;25&gt; | ❌ FAIL | ✅ PASS | DEF-01 |
| TC-BVA-31 | Cancellation & Refund | Cancel 14 days before departure → 25% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-32 | Cancellation & Refund | Cancel 8 days before departure → 25% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-33 | Cancellation & Refund | Cancel 7 days before departure → 25% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-34 | Cancellation & Refund | Cancel 6 days before departure → 0% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-35 | Cancellation & Refund | Cancel on departure day → 0% | As expected | ✅ PASS | ✅ PASS | - |
| TC-BVA-36 | Cancellation & Refund | Cancel 1 day after departure is rejected | As expected | ✅ PASS | ✅ PASS | - |

### Decision Table

| TC ID | Module | Test case (input -> expected) | Cycle 1 actual result | Cycle 1 | Cycle 2 | Defect |
|---|---|---|---|---|---|---|
| TC-DT-01 | Pricing & Discounts | Rule 1 - no group, no early bird, no coupon → 0% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-02 | Pricing & Discounts | Rule 2 - coupon only → 10% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-03 | Pricing & Discounts | Rule 3 - early bird only → 5% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-04 | Pricing & Discounts | Rule 4 - early bird + coupon → 15% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-05 | Pricing & Discounts | Rule 5 - group only → 10% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-06 | Pricing & Discounts | Rule 6 - group + coupon → 20% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-07 | Pricing & Discounts | Rule 7 - group + early bird → 15% | As expected | ✅ PASS | ✅ PASS | - |
| TC-DT-08 | Pricing & Discounts | Rule 8 - all three (25%) capped → 20% | expected: &lt;20&gt; but was: &lt;25&gt; | ❌ FAIL | ✅ PASS | DEF-02 |

### State Transition

| TC ID | Module | Test case (input -> expected) | Cycle 1 actual result | Cycle 1 | Cycle 2 | Defect |
|---|---|---|---|---|---|---|
| TC-ST-01 | Booking Lifecycle | New booking starts in CREATED | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-02 | Booking Lifecycle | CREATED → PAYMENT_PENDING on initiatePayment | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-03 | Booking Lifecycle | PAYMENT_PENDING → CONFIRMED on paymentSuccess and seats are reserved | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-04 | Booking Lifecycle | PAYMENT_PENDING → PAYMENT_FAILED on first failure | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-05 | Booking Lifecycle | PAYMENT_FAILED → PAYMENT_PENDING on retry | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-06 | Booking Lifecycle | Third payment failure → EXPIRED | expected: &lt;EXPIRED&gt; but was: &lt;PAYMENT_FAILED&gt; | ❌ FAIL | ✅ PASS | DEF-04 |
| TC-ST-07 | Booking Lifecycle | CONFIRMED → COMPLETED after the trip | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-08 | Booking Lifecycle | CONFIRMED → CANCELLED releases reserved seats | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-09 | Booking Lifecycle | Cancel a COMPLETED booking is rejected | Expected java.lang.IllegalStateException to be thrown, but nothing was thrown. | ❌ FAIL | ✅ PASS | DEF-03 |
| TC-ST-10 | Booking Lifecycle | paymentSuccess on a CREATED booking is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-11 | Booking Lifecycle | Retry payment on an EXPIRED booking is rejected | Expected java.lang.IllegalStateException to be thrown, but nothing was thrown. | ❌ FAIL | ✅ PASS | DEF-04 |
| TC-ST-12 | Booking Lifecycle | Complete a CANCELLED booking is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-13 | Booking Lifecycle | PAYMENT_PENDING → CANCELLED (seats were never reserved) | As expected | ✅ PASS | ✅ PASS | - |
| TC-ST-14 | Booking Lifecycle | PAYMENT_FAILED → CANCELLED | As expected | ✅ PASS | ✅ PASS | - |

### Functional

| TC ID | Module | Test case (input -> expected) | Cycle 1 actual result | Cycle 1 | Cycle 2 | Defect |
|---|---|---|---|---|---|---|
| TC-FN-01 | Pricing & Discounts | 2 adults + 1 child, Rs 5000 package, no discount → Rs 13125.00 incl. GST | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-02 | Pricing & Discounts | Group of 5 (all fare types), early bird + coupon, Rs 10000 package → Rs 27720.00 | expected: &lt;27720.00&gt; but was: &lt;25987.50&gt; | ❌ FAIL | ✅ PASS | DEF-02 |
| TC-FN-03 | Pricing & Discounts | Coupon ' tour10 ' (lowercase with spaces) is accepted | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-04 | Pricing & Discounts | Unknown coupon TOUR50 gives no discount | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-05 | Booking | Booking 4 travellers when only 3 seats left is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-06 | Cancellation & Refund | Refund on Rs 27720 paid, cancelled 20 days before → Rs 13860.00 | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-07 | Pricing & Discounts | Negative traveller age is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-08 | Booking | Booking with 11 travellers is rejected | As expected | ✅ PASS | ✅ PASS | - |
| TC-FN-09 | Booking | Valid booking gets an ID and the correct amount | As expected | ✅ PASS | ✅ PASS | - |

