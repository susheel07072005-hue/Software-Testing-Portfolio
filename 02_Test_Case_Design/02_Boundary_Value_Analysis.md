# Boundary Value Analysis (BVA)

**Idea:** Defects cluster at the edges of input ranges, where a developer writes `<` instead of `<=`. For each range we test **min−1, min, min+1, max−1, max, max+1**.

Automated in: `05_Test_Automation/src/test/java/com/msrit/travel/BoundaryValueTest.java`

## 1. Number of Travellers (valid range 1–10)

```
   0  |  1   2  ......  9   10  |  11
 invalid |<------ valid ------>| invalid
```

| TC ID | Value | Position | Expected |
|---|---|---|---|
| TC-BVA-01 | 0 | min−1 | Rejected |
| TC-BVA-02 | 1 | min | Accepted |
| TC-BVA-03 | 2 | min+1 | Accepted |
| TC-BVA-04 | 9 | max−1 | Accepted |
| TC-BVA-05 | 10 | max | Accepted |
| TC-BVA-06 | 11 | max+1 | Rejected |

## 2. Password Length (valid range 8–16)

| TC ID | Length | Position | Expected |
|---|---|---|---|
| TC-BVA-07 | 7 | min−1 | Rejected |
| TC-BVA-08 | 8 | min | Accepted |
| TC-BVA-09 | 9 | min+1 | Accepted |
| TC-BVA-10 | 15 | max−1 | Accepted |
| TC-BVA-11 | 16 | max | Accepted |
| TC-BVA-12 | 17 | max+1 | Rejected |

## 3. Fare Category by Age (Infant 0–1, Child 2–11, Adult 12–59, Senior 60+)

There are three internal boundaries: 2, 12 and 60.

| TC ID | Age | Boundary | Expected Category |
|---|---|---|---|
| TC-BVA-13 | 1 | Infant max | INFANT |
| TC-BVA-14 | 2 | Child min | CHILD |
| TC-BVA-15 | 11 | Child max | CHILD |
| TC-BVA-16 | 12 | Adult min | ADULT |
| TC-BVA-17 | 13 | Adult min+1 | ADULT |
| TC-BVA-18 | 59 | Adult max | ADULT |
| TC-BVA-19 | 60 | Senior min | SENIOR |
| TC-BVA-20 | 61 | Senior min+1 | SENIOR |

## 4. Travel Date Window (tomorrow to today + 365)

Reference date: **today = 01-12-2026**

| TC ID | Travel date | Position | Expected |
|---|---|---|---|
| TC-BVA-21 | today + 0 | min−1 | Rejected |
| TC-BVA-22 | today + 1 | min | Accepted |
| TC-BVA-23 | today + 2 | min+1 | Accepted |
| TC-BVA-24 | today + 364 | max−1 | Accepted |
| TC-BVA-25 | today + 365 | max | Accepted |
| TC-BVA-26 | today + 366 | max+1 | Rejected |

## 5. Refund Slabs (days between cancellation and departure)

```
 <0 (not allowed) | 0 ... 6 → 0% | 7 ... 14 → 25% | 15 ... 30 → 50% | 31+ → 90%
```

| TC ID | Days before departure | Boundary | Expected Refund |
|---|---|---|---|
| TC-BVA-27 | 31 | 90% slab min | 90% |
| TC-BVA-28 | 30 | 50% slab max | 50% |
| TC-BVA-29 | 16 | 50% slab min+1 | 50% |
| TC-BVA-30 | 15 | 50% slab min | 50% |
| TC-BVA-31 | 14 | 25% slab max | 25% |
| TC-BVA-32 | 8 | 25% slab min+1 | 25% |
| TC-BVA-33 | 7 | 25% slab min | 25% |
| TC-BVA-34 | 6 | 0% slab max | 0% |
| TC-BVA-35 | 0 | 0% slab min | 0% |
| TC-BVA-36 | −1 | Below valid range | Rejected (`IllegalArgumentException`) |

> BVA found **4 of the 8 defects** in Cycle 1 (TC-BVA-05, 16, 21, 30). Every one was an off-by-one comparison in the code.
