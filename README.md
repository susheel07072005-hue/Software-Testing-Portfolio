# Software Testing Portfolio: Travel and Tour Package Booking System

![Tests](https://img.shields.io/badge/tests-87%20passed-brightgreen) ![JUnit](https://img.shields.io/badge/JUnit-5.10-blue) ![Java](https://img.shields.io/badge/Java-17-orange)

## Team Details

| Name | USN | Role |
|---|---|---|
| Shreyas | 1MS23IS118 | Test Automation (JUnit) & Defect Reporting |
| Susheel Kumar Naik S | 1MS23IS132 | Test Plan & Documentation, Test Case Design (Decision Table, State Transition) |
| Shrajan Prabhu | 1MS23IS116 | Test Case Design (EP, BVA) |

- **Subject:** Software Testing (Other Component)
- **Department:** Information Science and Engineering, M S Ramaiah Institute of Technology, Bengaluru
- **Semester / Section:** 7th Semester, Section B
- **Faculty:** Dr. Pushpalatha M. N.

## Objectives

1. Understand the Travel and Tour Package Booking System and identify the modules and functions to test.
2. Prepare a structured test plan (scope, approach, schedule, entry/exit criteria, risks).
3. Design test cases using black-box techniques: **Equivalence Partitioning, Boundary Value Analysis, Decision Tables and State Transition Testing**.
4. Execute the test cases, record actual results and report defects.
5. Automate the test cases with **JUnit 5** and measure code coverage.
6. Summarise the results in a test execution report.

## Project Description

The **Travel and Tour Package Booking System** lets customers register, search tour packages, book a package for a group of travellers, pay online, and cancel for a refund. The business rules we tested:

| Area | Rule |
|---|---|
| Travellers per booking | 1 to 10 |
| Travel date | From tomorrow up to 365 days ahead |
| Fare by age | Infant (0–1) free · Child (2–11) 50% · Adult (12–59) 100% · Senior (60+) 80% |
| Discounts | Group of 5+ = 10% · Early bird (30+ days ahead) = 5% · Coupon `TOUR10` = 10% · **capped at 20%** |
| Tax | 5% GST on the discounted amount |
| Refund | >30 days = 90% · 15–30 days = 50% · 7–14 days = 25% · <7 days = 0% |
| Payment | Booking expires after 3 failed payment attempts |
| Registration | Valid email · 10-digit mobile starting 6–9 · password 8–16 characters with letters and digits |

We wrote the system's core business logic in Java (`05_Test_Automation/src/main`) and tested it with JUnit 5 (`05_Test_Automation/src/test`).

### Modules under test

1. User Registration and Login
2. Package Search and Filter
3. Booking (travellers, travel date, seat availability)
4. Pricing and Discounts
5. Payment and Booking Lifecycle
6. Cancellation and Refund

## Results at a Glance

| | Cycle 1 (v1.0) | Cycle 2 (v2.0, after fixes) |
|---|---|---|
| Test cases executed | 87 | 87 |
| Passed | 77 | 87 |
| Failed | 10 | 0 |
| Defects found | 8 | 8 fixed and verified |

## Repository Structure

```
Software-Testing-Portfolio
├── README.md
├── 01_Test_Plan/            Test plan (scope, approach, schedule, risks)
├── 02_Test_Case_Design/     EP, BVA, Decision Table, State Transition
├── 03_Test_Execution/       Execution log (87 test cases, 2 cycles) + CSV
├── 04_Defect_Reports/       8 defect reports with severity, steps, fix
├── 05_Test_Automation/      Java application + JUnit 5 tests (Maven)
├── 06_Test_Reports/         Test execution summary report
├── 07_Screenshots/          JUnit runs, coverage report, defect evidence
└── Conclusion.md
```

## How to Run the Tests

```bash
cd 05_Test_Automation
mvn test                       # runs all 87 JUnit tests + JaCoCo coverage
# coverage report: target/site/jacoco/index.html
```
Or open `05_Test_Automation` in IntelliJ IDEA → right-click `src/test/java` → **Run 'All Tests'**.

## Tools Used

Java 17 · JUnit 5 (parameterized tests) · Maven · JaCoCo (code coverage) · PIT (mutation testing, optional) · IntelliJ IDEA · Git & GitHub
