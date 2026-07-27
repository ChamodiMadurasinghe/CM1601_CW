package org.example.coursework;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validId_isAcceptedAndUppercased() {
        assertEquals("P001", Validator.validateId("p001", List.of()));
    }

    @Test
    void wrongFormatId_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("PART1", List.of()));
    }

    @Test
    void emptyId_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("  ", List.of()));
    }

    @Test
    void duplicateId_isRejected() {
        List<String> existing = Arrays.asList("P001", "P002");
        assertThrows(IllegalArgumentException.class, () -> Validator.validateId("p001", existing));
    }

    @Test
    void emptyName_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateName(""));
    }

    @Test
    void emptyBrand_defaultsToNull() {
        assertEquals("NULL", Validator.validateBrand(""));
    }

    @Test
    void priceWithRsPrefix_isParsedCorrectly() {
        assertEquals(3500.0, Validator.validatePrice("Rs. 3500"));
    }

    @Test
    void negativePrice_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validatePrice("-100"));
    }

    @Test
    void nonNumericPrice_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validatePrice("abc"));
    }

    @Test
    void negativeQuantity_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateQuantity("-5"));
    }

    @Test
    void validQuantity_isParsed() {
        assertEquals(10, Validator.validateQuantity("10"));
    }

    @Test
    void dateWithSlashes_isNormalizedToIso() {
        assertEquals("2026-07-20", Validator.validateDate("2026/07/20"));
    }

    @Test
    void badDate_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validateDate("20/07/2026"));
    }

    @Test
    void blankThreshold_defaultsToTen() {
        assertEquals(Part.defaultThreshold, Validator.validateThreshold(""));
    }

    @Test
    void validThreshold_isParsed() {
        assertEquals(5, Validator.validateThreshold("5"));
    }
}
