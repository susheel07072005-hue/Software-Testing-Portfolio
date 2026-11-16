# Test Execution Summary Report

| Item | Detail |
|---|---|
| Project | Travel and Tour Package Booking System |
| Report ID | TSR-TTBS-01 |
| Test cycles | Cycle 1 on build v1.0, Cycle 2 on build v2.0 |
| Prepared by | Susheel Kumar Naik S |
| Date | 16-11-2026 |

## 1. Overall Result

| Metric | Cycle 1 (v1.0) | Cycle 2 (v2.0) |
|---|---|---|
| Test cases planned | 87 | 87 |
| Test cases executed | 87 (100%) | 87 (100%) |
| Passed | 77 | 87 |
| Failed | 10 | 0 |
| Blocked | 0 | 0 |
| **Pass rate** | **88.5%** | **100%** |
| New defects found | 8 | 0 |
| Open defects at end of cycle | 8 | 0 |

## 2. Results by Testing Technique (Cycle 1)

| Technique | Test cases | Passed | Failed | Defects found |
|---|---|---|---|---|
| Equivalence Partitioning | 20 | 19 | 1 | 1 (DEF-08) |
| Boundary Value Analysis | 36 | 32 | 4 | 4 (DEF-01, 05, 06, 07) |
| Decision Table | 8 | 7 | 1 | 1 (DEF-02) |
| State Transition | 14 | 11 | 3 | 2 (DEF-03, 04) |
| Functional | 9 | 8 | 1 | (DEF-02, already counted) |
| **Total** | **87** | **77** | **10** | **8** |

## 3. Results by Module (Cycle 1)

| Module | Test cases | Failed |
|---|---|---|
| Registration (email, phone, password) | 18 | 0 |
| Package Search | 5 | 1 |
| Booking (travellers, dates, seats) | 18 | 2 |
| Pricing & Discounts | 21 | 3 |
| Cancellation & Refund | 11 | 1 |
| Booking Lifecycle | 14 | 3 |

## 4. Defects by Severity

| Severity | Count | Defect IDs |
|---|---|---|
| Critical | 0 | – |
| High | 3 | DEF-01, DEF-02, DEF-03 |
| Medium | 4 | DEF-04, DEF-05, DEF-06, DEF-07 |
| Low | 1 | DEF-08 |
| **Total** | **8** | All closed in v2.0 |

## 5. Code Coverage (JaCoCo, build v2.0)

_Fill in from `05_Test_Automation/target/site/jacoco/index.html` after running `mvn test`, and save a screenshot in `07_Screenshots/`._

| Class | Instruction coverage | Branch coverage |
|---|---|---|
| InputValidator | _%_ | _%_ |
| PriceCalculator | _%_ | _%_ |
| RefundCalculator | _%_ | _%_ |
| PackageSearch | _%_ | _%_ |
| BookingService | _%_ | _%_ |
| **Total** | _%_ | _%_ |

## 6. Exit Criteria Check

| Criterion (from Test Plan §7) | Status |
|---|---|
| 100% of planned test cases executed | ✅ Met (87/87) |
| All Critical and High defects fixed and retested | ✅ Met (3/3 High closed) |
| Pass rate ≥ 95% in final cycle | ✅ Met (100%) |
| Statement coverage ≥ 80% on service classes | _Check JaCoCo report_ |

## 7. Observations

1. **Boundary values were the weakest area.** Half the defects (4 of 8) were off-by-one comparisons (`<` vs `<=`, `>` vs `>=`), and BVA caught all of them. EP alone would have missed every one, because values in the middle of a class (5 travellers, age 30, 20 days) behave correctly.
2. **The money-related defects were the most serious.** DEF-01 (refund slab) and DEF-02 (discount cap) cause wrong amounts on real transactions. In TC-FN-02, the missing cap under-charged one booking by ₹1,732.50.
3. **Combinations matter.** DEF-02 appears only when all three discount conditions are true (decision table Rule R8). Testing each discount on its own would not have found it.
4. **Negative state transitions found a serious defect.** DEF-03 (cancelling a completed trip) was found only by testing a transition that should be rejected. Valid paths alone were not enough.
5. **One root cause can fail several tests.** DEF-04 caused both TC-ST-06 and TC-ST-11 to fail, so 10 failures map to 8 defects.

## 8. Recommendation

Build v2.0 meets the exit criteria. The full regression suite of 87 tests passes, and all 8 defects are verified fixed. **The build is recommended for release** for the modules in scope.
