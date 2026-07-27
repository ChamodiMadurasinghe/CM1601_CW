package org.example.coursework;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validIdAcceptedAndUppercased() {
        assertEquals("P001", Validator.validateId("p001", List.of()));
    }

    @Test
    void wrongFormatIdRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("PART1", List.of()));
    }

    @Test
    void emptyIdRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("  ", List.of()));
    }

    @Test
    void duplicateIdRejected() {
        List<String> existing = Arrays.asList("P001", "P002");
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("p001", existing));
    }

    @Test
    void emptyNameRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateName(""));
    }

    @Test
    void emptyBrand_defaultsToNull() {
        assertEquals("NULL", Validator.validateBrand(""));
    }

    @Test
    void priceParsedCorrectly() {
        assertEquals(3500.0, Validator.validatePrice("Rs. 3500"));
    }

    @Test
    void negativePriceRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validatePrice("-100"));
    }

    @Test
    void nonNumericPriceRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validatePrice("abc"));
    }

    @Test
    void negativeQuantityRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateQuantity("-5"));
    }

    @Test
    void validQuantityParsed() {
        assertEquals(10, Validator.validateQuantity("10"));
    }

    @Test
    void dateNormalizedToIso() {
        assertEquals("2026-07-20", Validator.validateDate("2026/07/20"));
    }

    @Test
    void wrongDateRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateDate("20/07/2026"));
    }

    @Test
    void blankThreshold_defaultsToTen() {
        assertEquals(Part.defaultThreshold, Validator.validateThreshold(""));
    }

    @Test
    void validThresholdParsed() {
        assertEquals(5, Validator.validateThreshold("5"));
    }
}
