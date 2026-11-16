# Conclusion

We tested the core business logic of the **Travel and Tour Package Booking System** across six modules: registration, package search, booking, pricing and discounts, the payment and booking lifecycle, and cancellation and refund.

## What we did

- Wrote an IEEE 829 style **test plan** with scope, approach, entry and exit criteria, and risks.
- Designed **87 test cases** with four black-box techniques: Equivalence Partitioning (20), Boundary Value Analysis (36), Decision Table (8) and State Transition (14), plus 9 functional end-to-end checks.
- **Automated every test case in JUnit 5**, using parameterized tests so each row of a design table maps directly to one test run.
- Ran **two test cycles**. Cycle 1 on v1.0 passed 77 of 87 tests (88.5%) and found **8 defects** (3 High, 4 Medium, 1 Low). After the fixes, Cycle 2 on v2.0 passed **all 87 tests (100%)**.

## What we learned

1. **Boundary Value Analysis gave the best return.** Half the defects were off-by-one errors at range edges: the 15-day refund slab, the 10-traveller limit, the age-12 fare boundary and the same-day travel date. Middle-of-range values hid every one of them.
2. **Decision tables find combination bugs.** The discount cap defect appeared only when all three discounts applied together. In a real booking it under-charged ₹1,732.50.
3. **Invalid transitions must be tested.** Being able to cancel a completed trip was a serious defect, and only a negative state-transition test exposed it.
4. **Automated tests make retesting cheap.** Once the tests were written, re-running all 87 after the fixes took seconds and confirmed that no fix broke anything else (regression).
5. **Tests must be repeatable.** Using a fixed reference date and `BigDecimal` for money made every result deterministic and exact.

## Future scope

- UI automation of the booking flow with **Selenium WebDriver**
- API testing of booking and payment endpoints with **Postman**
- Use **PIT mutation testing** results to strengthen weak assertions
- Performance testing of package search under load with **JMeter**
