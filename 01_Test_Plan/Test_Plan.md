# Test Plan: Travel and Tour Package Booking System

_Format based on IEEE 829 Test Plan._

| Item | Detail |
|---|---|
| Test Plan ID | TP-TTBS-01 |
| Version | 1.0 |
| Prepared by | Susheel Kumar Naik S |
| Reviewed by | Team |
| Date | 16-11-2026 |

## 1. Introduction

This plan describes how the team will test the **Travel and Tour Package Booking System (TTBS)**. With TTBS, customers can register, search tour packages, book them for a group, pay online and cancel for a refund. Testing focuses on the business rules where mistakes cost money or break bookings: pricing, discounts, refunds, input validation and the booking lifecycle.

## 2. Test Items

| Item | Java class |
|---|---|
| Input validation (registration and booking) | `InputValidator` |
| Package search and filter | `PackageSearch` |
| Fare and discount calculation | `PriceCalculator` |
| Cancellation refund | `RefundCalculator` |
| Booking creation and lifecycle | `BookingService`, `Booking`, `TourPackage` |

## 3. Features to be Tested

| ID | Module | Feature | Technique(s) |
|---|---|---|---|
| F1 | Registration | Email, phone and password validation | EP, BVA |
| F2 | Package Search | Filter by destination (case-insensitive), budget, duration | EP |
| F3 | Booking | Traveller count 1–10, travel date window, seat availability | EP, BVA |
| F4 | Pricing | Fare by age category | BVA |
| F5 | Discounts | Group, early-bird and coupon discounts, 20% cap | Decision Table |
| F6 | Payment / Lifecycle | Booking states, payment retries, expiry after 3 failures | State Transition |
| F7 | Cancellation | Refund % by days before departure, refund amount | BVA, Functional |

## 4. Features Not to be Tested

- User interface layout and look and feel
- Real payment gateway integration (payment success/failure is simulated)
- Performance, load and security testing
- Email/SMS notifications
- Admin package management screens

## 5. Approach

- **Level:** Unit and component testing of the business logic.
- **Type:** Black-box functional testing. Test cases are designed from the requirements (Section 3 of the README), not from the code.
- **Techniques:**
  - *Equivalence Partitioning*: one representative value per valid and invalid input class.
  - *Boundary Value Analysis*: min−1, min, min+1, max−1, max, max+1 for every numeric range.
  - *Decision Table*: all 8 combinations of the three discount conditions.
  - *State Transition*: every valid transition, plus key invalid transitions, in the booking lifecycle.
- **Automation:** Every designed test case is automated as a JUnit 5 test. Parameterized tests (`@ParameterizedTest` + `@CsvSource`) map each table row from the design documents to one test run.
- **Coverage:** JaCoCo measures statement and branch coverage. PIT mutation testing is optional.
- **Cycles:** Cycle 1 runs on the first build (v1.0). Developers fix the defects, then Cycle 2 re-runs the full suite on v2.0 (retest + regression).

## 6. Pass / Fail Criteria

- A test case **passes** when the actual result equals the expected result and no unexpected exception is thrown.
- A test case **fails** in all other cases. Every failure is logged as a defect in `04_Defect_Reports`.

## 7. Entry and Exit Criteria

**Entry criteria**
- Requirements and business rules are frozen (README → Project Description).
- The build compiles and the test environment is ready (JDK 17+, Maven, IntelliJ).
- Test cases are designed and reviewed.

**Exit criteria**
- 100% of planned test cases executed.
- All Critical and High severity defects fixed and retested.
- Pass rate ≥ 95% in the final cycle.
- Statement coverage ≥ 80% on the service classes.

## 8. Suspension and Resumption Criteria

- Testing is suspended if the build fails to compile or more than 30% of tests fail because of one blocking defect.
- Testing resumes when a new build that fixes the blocker is delivered.

## 9. Test Deliverables

| Deliverable | Location |
|---|---|
| Test plan | `01_Test_Plan/` |
| Test case design documents | `02_Test_Case_Design/` |
| Test execution log | `03_Test_Execution/` |
| Defect reports | `04_Defect_Reports/` |
| Automated test scripts | `05_Test_Automation/src/test/java` |
| Test summary report | `06_Test_Reports/` |
| Screenshots / evidence | `07_Screenshots/` |

## 10. Test Environment

| Component | Version |
|---|---|
| OS | Windows 11 / Ubuntu 22.04 |
| Java | JDK 17 or later |
| Build tool | Apache Maven 3.9 |
| Test framework | JUnit 5.10 (Jupiter, Params) |
| Coverage | JaCoCo 0.8.12 |
| IDE | IntelliJ IDEA Community |
| Version control | Git, GitHub |

## 11. Roles and Responsibilities

| Member | Responsibility |
|---|---|
| Shreyas (1MS23IS118) | Java implementation, JUnit automation, coverage, defect reports |
| Susheel Kumar Naik S (1MS23IS132) | Test plan, README, decision table and state transition design, summary report |
| Shrajan Prabhu (1MS23IS116) | EP and BVA test case design |

## 12. Schedule

| Activity | Day |
|---|---|
| Repository setup, requirement study | Day 1 |
| Test plan | Day 2 |
| EP and BVA design | Day 3 |
| Decision table and state transition design | Day 4 |
| Implementation and JUnit automation | Day 5 |
| Cycle 1 execution, defect logging, fixes | Day 6 |
| Cycle 2 retest, summary report, conclusion | Day 7 |

## 13. Risks and Mitigation

| Risk | Impact | Mitigation |
|---|---|---|
| Ambiguous business rules (e.g. is "15 days" in the 50% slab?) | Wrong expected results | Write every rule with explicit inclusive/exclusive ranges before designing tests |
| Date-based tests pass or fail depending on the day they run | Flaky results | Use a fixed reference date (`TestData.TODAY = 2026-12-01`) |
| Money rounding errors | Wrong totals | Use `BigDecimal` with `HALF_UP` rounding, and assert exact amounts |
| Team availability during placement season | Schedule slip | Split work by technique so members can work in parallel |

## 14. Approvals

| Name | Role | Signature | Date |
|---|---|---|---|
| | Faculty | | |
