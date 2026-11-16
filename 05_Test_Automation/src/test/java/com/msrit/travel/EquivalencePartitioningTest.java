package com.msrit.travel;

import com.msrit.travel.model.TourPackage;
import com.msrit.travel.service.InputValidator;
import com.msrit.travel.service.PackageSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Equivalence Partitioning test cases. See 02_Test_Case_Design/01_Equivalence_Partitioning.md */
@DisplayName("Equivalence Partitioning")
class EquivalencePartitioningTest {

    private final InputValidator validator = new InputValidator();
    private final PackageSearch search = new PackageSearch();

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-EP-01: Traveller count 5 (valid class 1-10)', 5, true",
            "'TC-EP-02: Traveller count -3 (invalid class < 1)', -3, false",
            "'TC-EP-03: Traveller count 15 (invalid class > 10)', 15, false"
    })
    void travellerCount(String id, int count, boolean expected) {
        assertEquals(expected, validator.isValidTravellerCount(count));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(value = {
            "'TC-EP-04: Email shreyas@gmail.com (valid)', shreyas@gmail.com, true",
            "'TC-EP-05: Email without @ (invalid)', shreyasgmail.com, false",
            "'TC-EP-06: Email without domain extension (invalid)', shreyas@gmail, false",
            "'TC-EP-07: Empty email (invalid)', '', false",
            "'TC-EP-08: Null email (invalid)', NULL, false"
    }, nullValues = "NULL")
    void email(String id, String email, boolean expected) {
        assertEquals(expected, validator.isValidEmail(email));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-EP-09: Phone 9876543210 (valid)', 9876543210, true",
            "'TC-EP-10: Phone starting with 5 (invalid)', 5876543210, false",
            "'TC-EP-11: Phone with 5 digits (invalid)', 98765, false",
            "'TC-EP-12: Phone with letters (invalid)', 98765abcde, false"
    })
    void phone(String id, String phone, boolean expected) {
        assertEquals(expected, validator.isValidPhone(phone));
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "'TC-EP-13: Password with letters and digits (valid)', Travel2026, true",
            "'TC-EP-14: Password with no digit (invalid)', travelonly, false",
            "'TC-EP-15: Password with no letter (invalid)', 12345678, false"
    })
    void password(String id, String password, boolean expected) {
        assertEquals(expected, validator.isValidPassword(password));
    }

    @Test
    @DisplayName("TC-EP-16: Search 'Goa' returns both Goa packages")
    void searchByDestination() {
        List<TourPackage> result = search.search(TestData.catalogue(), "Goa", null, null, null);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("TC-EP-17: Search 'goa' (lowercase) returns both Goa packages")
    void searchIsCaseInsensitive() {
        List<TourPackage> result = search.search(TestData.catalogue(), "goa", null, null, null);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("TC-EP-18: Search 'Paris' (no such package) returns empty list")
    void searchNoMatch() {
        List<TourPackage> result = search.search(TestData.catalogue(), "Paris", null, null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("TC-EP-19: Negative budget is rejected")
    void negativeBudgetRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> search.search(TestData.catalogue(), "Goa", new BigDecimal("-1"), null, null));
    }

    @Test
    @DisplayName("TC-EP-20: Blank destination with budget 8000 returns packages priced <= 8000")
    void blankDestinationWithBudget() {
        List<TourPackage> result = search.search(TestData.catalogue(), "  ", new BigDecimal("8000"), null, null);
        assertEquals(3, result.size());
    }
}
