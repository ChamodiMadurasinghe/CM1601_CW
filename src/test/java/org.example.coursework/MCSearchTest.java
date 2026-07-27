package org.example.coursework;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MCSearchTest {

    private final MCSearch mcSearch = new MCSearch();

    private List<Part> sampleParts() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Chain Sprocket", "TVS", 3500.0, 12, "ENGINE", "2026-01-01", "NULL", 10));
        parts.add(new Part("P002", "Brake Pad", "Bajaj", 1200.0, 25, "BRAKES", "2026-01-01", "NULL", 10));
        parts.add(new Part("P003", "Battery 12V", "Exide", 9800.0, 3, "ELECTRICAL", "2026-01-01", "NULL", 5));
        return parts;
    }

    @Test
    void filterByCategory() {
        List<Part> results = mcSearch.search(sampleParts(), "ENGINE", null, null, null);
        assertEquals(1, results.size());
        assertEquals("P001", results.get(0).getId());
    }

    @Test
    void filterByPriceRange() {
        List<Part> results = mcSearch.search(sampleParts(), null, 1000.0, 4000.0, null);
        assertEquals(2, results.size()); // Chain Sprocket and Brake Pad
    }

    @Test
    void filterByKeyword() {
        List<Part> results = mcSearch.search(sampleParts(), null, null, null, "exide");
        assertEquals(1, results.size());
        assertEquals("P003", results.get(0).getId());
    }

    @Test
    void combiningThreeFilters() {
        List<Part> results = mcSearch.search(sampleParts(), "ENGINE", 1000.0, 5000.0, "chain");
        assertEquals(1, results.size());
        assertEquals("P001", results.get(0).getId());
    }

    @Test
    void returnsEmptyList() {
        List<Part> results = mcSearch.search(sampleParts(), "BODYWORK", null, null, null);
        assertTrue(results.isEmpty());
    }
}
