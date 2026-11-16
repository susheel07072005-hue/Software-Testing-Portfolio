# Test Automation: JUnit 5

This folder holds the Java implementation of the booking system's business logic, plus the JUnit 5 test suite: **87 test cases in 5 test classes**.

## Structure

```
05_Test_Automation
├── pom.xml
└── src
    ├── main/java/com/msrit/travel
    │   ├── model/      TourPackage, Traveller, Booking, BookingStatus
    │   └── service/    InputValidator, PriceCalculator, RefundCalculator,
    │                   PackageSearch, BookingService
    └── test/java/com/msrit/travel
        ├── EquivalencePartitioningTest.java   20 tests (TC-EP-01..20)
        ├── BoundaryValueTest.java             36 tests (TC-BVA-01..36)
        ├── DiscountDecisionTableTest.java      8 tests (TC-DT-01..08)
        ├── BookingStateTransitionTest.java    14 tests (TC-ST-01..14)
        ├── BookingAndPricingTest.java          9 tests (TC-FN-01..09)
        └── TestData.java                      shared fixed test data
```

## How the Design Maps to Code

Every row in a design table in `02_Test_Case_Design` becomes one line in a `@CsvSource`. The test ID is the first column, so it appears in the test report:

```java
@ParameterizedTest(name = "{0}")
@CsvSource({
        "'TC-BVA-27: Cancel 31 days before departure -> 90%', 31, 90",
        "'TC-BVA-28: Cancel 30 days before departure -> 50%', 30, 50",
        ...
})
void refundSlabs(String id, int daysBefore, int expectedPercent) {
    assertEquals(expectedPercent, refund.refundPercent(TODAY, TODAY.plusDays(daysBefore)));
}
```

All date-based tests use a fixed date, `TestData.TODAY = 2026-12-01`, so results don't change depending on the day the tests run.

## Running

**IntelliJ IDEA:** File → Open → select this `05_Test_Automation` folder (it imports as a Maven project) → right-click `src/test/java` → **Run 'All Tests'**. To get coverage, choose **More Run/Debug → Run with Coverage**.

**Command line (needs Maven):**
```bash
mvn test                                                   # all tests + JaCoCo report
# open target/site/jacoco/index.html

mvn test-compile org.pitest:pitest-maven:mutationCoverage  # optional: mutation testing
# open target/pit-reports/index.html
```

## JUnit 5 Features Used

| Feature | Where |
|---|---|
| `@Test`, `@DisplayName` | All classes |
| `@ParameterizedTest` + `@CsvSource` | EP, BVA, Decision Table |
| `nullValues = "NULL"` for null inputs | EP email, Decision Table coupon |
| `@BeforeEach` fresh fixture per test | State Transition |
| `assertThrows` for negative tests | Invalid transitions, invalid input |
| `assertEquals` on `BigDecimal` money | Functional pricing tests |
