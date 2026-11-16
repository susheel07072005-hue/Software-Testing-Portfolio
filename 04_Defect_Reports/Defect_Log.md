# Defect Log

**Application:** Travel and Tour Package Booking System  
**Build where found:** v1.0 (Test Cycle 1)  
**Build where fixed:** v2.0 (verified in Test Cycle 2)  
**Reported by:** Shreyas  

## Severity and Priority Definitions

| Severity | Meaning |
|---|---|
| Critical | System crash or data loss, no workaround |
| High | Wrong money calculation or a broken core business rule |
| Medium | A valid action is wrongly blocked or an invalid one is wrongly allowed, and a workaround exists |
| Low | Usability or convenience issue |

Priority (High / Medium / Low) is how urgently the defect must be fixed before release.

## Defect Summary

| Defect ID | Title | Module | Severity | Priority | Found by | Status |
|---|---|---|---|---|---|---|
| DEF-01 | Cancelling exactly 15 days before departure refunds 25% instead of 50% | Cancellation & Refund | High | High | TC-BVA-30 | Closed |
| DEF-02 | Combined discounts exceed the 20% cap | Pricing & Discounts | High | High | TC-DT-08, TC-FN-02 | Closed |
| DEF-03 | A COMPLETED booking can be cancelled | Booking Lifecycle | High | High | TC-ST-09 | Closed |
| DEF-04 | Booking expires after 4 failed payments instead of 3 | Booking Lifecycle | Medium | High | TC-ST-06, TC-ST-11 | Closed |
| DEF-05 | 12-year-old traveller charged child fare | Pricing & Discounts | Medium | Medium | TC-BVA-16 | Closed |
| DEF-06 | Booking for exactly 10 travellers is rejected | Booking | Medium | Medium | TC-BVA-05 | Closed |
| DEF-07 | Same-day travel date is accepted | Booking | Medium | Medium | TC-BVA-21 | Closed |
| DEF-08 | Destination search is case-sensitive | Package Search | Low | Medium | TC-EP-17 | Closed |

**By severity:** Critical 0 · High 3 · Medium 4 · Low 1 · **Total 8**  
**By technique:** BVA 4 · State Transition 2 · Decision Table 1 · EP 1

---

## DEF-01: Cancelling exactly 15 days before departure refunds 25% instead of 50%

| Field | Detail |
|---|---|
| Module | Cancellation & Refund (`RefundCalculator.refundPercent`) |
| Severity / Priority | High / High |
| Test case | TC-BVA-30 |
| Environment | JDK 17, JUnit 5.10, build v1.0 |

**Steps to reproduce**
1. Create a confirmed booking with departure date 16-12-2026.
2. Cancel it on 01-12-2026 (exactly 15 days before departure).
3. Check the refund percentage.

**Expected:** 50% (the 15–30 day slab includes 15).  
**Actual:** 25%.  
**Root cause:** An off-by-one comparison excluded the lower boundary of the slab.

```java
// v1.0 (defect)
if (days > 15) return 50;
// v2.0 (fix)
if (days >= 15) return 50;
```
**Impact:** Every customer who cancels exactly 15 days before departure loses 25% of the booking amount.

---

## DEF-02: Combined discounts exceed the 20% cap

| Field | Detail |
|---|---|
| Module | Pricing & Discounts (`PriceCalculator.discountPercent`) |
| Severity / Priority | High / High |
| Test cases | TC-DT-08 (Rule R8), TC-FN-02 |

**Steps to reproduce**
1. Book a package for 6 travellers (group discount, 10%).
2. Book 45 days before travel (early bird, 5%).
3. Apply coupon `TOUR10` (10%).

**Expected:** 20% (cap). For TC-FN-02 the total is ₹27,720.00.  
**Actual:** 25%. For TC-FN-02 the total is ₹25,987.50, so the agency under-charges by ₹1,732.50.  
**Root cause:** The discounts were added but the cap was never applied.

```java
// v1.0 (defect)
return discount;
// v2.0 (fix)
return Math.min(discount, MAX_DISCOUNT_PERCENT);
```

---

## DEF-03: A COMPLETED booking can be cancelled

| Field | Detail |
|---|---|
| Module | Booking Lifecycle (`BookingService.cancel`) |
| Severity / Priority | High / High |
| Test case | TC-ST-09 |

**Steps to reproduce**
1. Create a booking, initiate payment, payment succeeds (CONFIRMED).
2. Mark the trip as complete (COMPLETED).
3. Call cancel.

**Expected:** `IllegalStateException`. The booking stays COMPLETED.  
**Actual:** No exception. The booking moves to CANCELLED, which would trigger a refund for a trip the customer has already taken.  
**Root cause:** `cancel()` only blocked bookings that were already CANCELLED, instead of allowing only the states listed as cancellable.

```java
// v1.0 (defect)
if (booking.getStatus() == BookingStatus.CANCELLED) {
    throw new IllegalStateException("Booking already cancelled");
}
// v2.0 (fix): whitelist of states that may be cancelled
requireStatus(booking, CANCELLABLE, "cancel");   // CREATED, PAYMENT_PENDING, PAYMENT_FAILED, CONFIRMED
```

---

## DEF-04: Booking expires after 4 failed payments instead of 3

| Field | Detail |
|---|---|
| Module | Booking Lifecycle (`BookingService.paymentFailure`) |
| Severity / Priority | Medium / High |
| Test cases | TC-ST-06, TC-ST-11 |

**Steps to reproduce**
1. Create a booking.
2. Repeat initiate payment, then payment failure, 3 times.
3. Check the status. Then try to initiate payment again.

**Expected:** EXPIRED after the 3rd failure. A further retry is rejected.  
**Actual:** PAYMENT_FAILED after the 3rd failure, and a 4th attempt is allowed.  
**Root cause:** Off-by-one in the attempt check.

```java
// v1.0 (defect)
if (booking.getFailedPaymentAttempts() > MAX_PAYMENT_ATTEMPTS) {
// v2.0 (fix)
if (booking.getFailedPaymentAttempts() >= MAX_PAYMENT_ATTEMPTS) {
```
**Note:** Two test cases failed for one root cause. TC-ST-11 failed because the booking never reached EXPIRED.

---

## DEF-05: 12-year-old traveller charged child fare

| Field | Detail |
|---|---|
| Module | Pricing & Discounts (`PriceCalculator.categorize`) |
| Severity / Priority | Medium / Medium |
| Test case | TC-BVA-16 |

**Steps to reproduce:** Categorise a traveller aged 12.  
**Expected:** ADULT (child fare covers ages 2–11).  
**Actual:** CHILD, so the traveller pays 50% instead of 100%.  
**Root cause:**
```java
// v1.0 (defect)
if (age <= 12) return FareCategory.CHILD;
// v2.0 (fix)
if (age < 12) return FareCategory.CHILD;
```

---

## DEF-06: Booking for exactly 10 travellers is rejected

| Field | Detail |
|---|---|
| Module | Booking (`InputValidator.isValidTravellerCount`) |
| Severity / Priority | Medium / Medium |
| Test case | TC-BVA-05 |

**Steps to reproduce:** Validate a traveller count of 10.  
**Expected:** Accepted (maximum allowed is 10).  
**Actual:** Rejected. Customers must split a group of 10 into two bookings, which also loses them the group discount.  
**Root cause:**
```java
// v1.0 (defect)
return count >= MIN_TRAVELLERS && count < MAX_TRAVELLERS;
// v2.0 (fix)
return count >= MIN_TRAVELLERS && count <= MAX_TRAVELLERS;
```

---

## DEF-07: Same-day travel date is accepted

| Field | Detail |
|---|---|
| Module | Booking (`InputValidator.isValidTravelDate`) |
| Severity / Priority | Medium / Medium |
| Test case | TC-BVA-21 |

**Steps to reproduce:** On 01-12-2026, validate travel date 01-12-2026.  
**Expected:** Rejected (bookings must be at least 1 day in advance).  
**Actual:** Accepted.  
**Root cause:**
```java
// v1.0 (defect)
return !travelDate.isBefore(today) && ...
// v2.0 (fix)
return travelDate.isAfter(today) && ...
```

---

## DEF-08: Destination search is case-sensitive

| Field | Detail |
|---|---|
| Module | Package Search (`PackageSearch.search`) |
| Severity / Priority | Low / Medium |
| Test case | TC-EP-17 |

**Steps to reproduce:** Search the catalogue for `goa`.  
**Expected:** 2 packages (Goa Beach Escape, North Goa Adventure).  
**Actual:** 0 packages. Only `Goa` with a capital G works.  
**Root cause:** The destination was compared without normalising case.
```java
// v1.0 (defect)
p.getDestination().contains(destination.trim())
// v2.0 (fix)
p.getDestination().toLowerCase(Locale.ROOT).contains(query)   // query is lower-cased too
```
