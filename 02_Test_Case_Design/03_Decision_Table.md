# Decision Table Testing: Discount Rules

**Idea:** When the output depends on a combination of conditions, list every combination in a table and test each one (a "rule"). With 3 yes/no conditions there are 2³ = 8 rules.

Automated in: `05_Test_Automation/src/test/java/com/msrit/travel/DiscountDecisionTableTest.java`

## Conditions and Actions

| | Description |
|---|---|
| **C1** | Group booking: 5 or more travellers |
| **C2** | Early bird: booked 30 or more days before travel |
| **C3** | Valid coupon `TOUR10` applied |
| **A1** | Group discount 10% |
| **A2** | Early-bird discount 5% |
| **A3** | Coupon discount 10% |
| **A4** | Total discount capped at 20% |

## Decision Table

| | R1 | R2 | R3 | R4 | R5 | R6 | R7 | R8 |
|---|---|---|---|---|---|---|---|---|
| **C1** Group (5+) | N | N | N | N | Y | Y | Y | Y |
| **C2** Early bird (30+ days) | N | N | Y | Y | N | N | Y | Y |
| **C3** Coupon TOUR10 | N | Y | N | Y | N | Y | N | Y |
| A1 Group 10% | | | | | ✔ | ✔ | ✔ | ✔ |
| A2 Early bird 5% | | | ✔ | ✔ | | | ✔ | ✔ |
| A3 Coupon 10% | | ✔ | | ✔ | | ✔ | | ✔ |
| A4 Cap applied | | | | | | | | ✔ |
| **Final discount** | **0%** | **10%** | **5%** | **15%** | **10%** | **20%** | **15%** | **20%** |

## Test Cases

| TC ID | Rule | Travellers | Days in advance | Coupon | Expected Discount |
|---|---|---|---|---|---|
| TC-DT-01 | R1 | 2 | 10 | none | 0% |
| TC-DT-02 | R2 | 2 | 10 | TOUR10 | 10% |
| TC-DT-03 | R3 | 2 | 45 | none | 5% |
| TC-DT-04 | R4 | 2 | 45 | TOUR10 | 15% |
| TC-DT-05 | R5 | 6 | 10 | none | 10% |
| TC-DT-06 | R6 | 6 | 10 | TOUR10 | 20% |
| TC-DT-07 | R7 | 6 | 45 | none | 15% |
| TC-DT-08 | R8 | 6 | 45 | TOUR10 | 20% (10+5+10 = 25, capped) |

> Rule R8 is the only rule where the cap applies, and it found **DEF-02**: v1.0 gave 25% because the cap was missing. A tester who checked only single conditions would have missed it.

## Worked example (TC-FN-02, Rule R8)

Package price ₹10,000 per adult. Travellers aged 35, 33, 8, 1 and 65, booked 45 days early, coupon TOUR10.

| Step | Amount |
|---|---|
| Adult 35 + Adult 33 | ₹20,000 |
| Child 8 (50%) | ₹5,000 |
| Infant 1 (free) | ₹0 |
| Senior 65 (80%) | ₹8,000 |
| Subtotal | ₹33,000 |
| Discount 20% (capped) | −₹6,600 → ₹26,400 |
| GST 5% | +₹1,320 |
| **Total** | **₹27,720.00** |
