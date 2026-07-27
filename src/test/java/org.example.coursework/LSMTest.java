package org.example.coursework;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LSMTest {

    private final LSM lsm = new LSM();

    @Test
    void reportedLowStock() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Chain", "TVS", 500.0, 3, "ENGINE", "2026-01-01", "NULL", 5));   // 3 <= 5 -> low
        parts.add(new Part("P002", "Bulb", "Osram", 300.0, 20, "ELECTRICAL", "2026-01-01", "NULL", 10)); // 20 > 10 -> ok

        List<Part> lowStock = lsm.findLSParts(parts);

        assertEquals(1, lowStock.size());
        assertEquals("P001", lowStock.get(0).getId());
    }

    @Test
    void differentPartsDifferentThresholds() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Chain", "TVS", 500.0, 8, "ENGINE", "2026-01-01", "NULL", 5));    // 8 > 5 -> ok
        parts.add(new Part("P002", "Battery", "Exide", 9000.0, 8, "ELECTRICAL", "2026-01-01", "NULL", 10)); // 8 <= 10 -> low

        List<Part> lowStock = lsm.findLSParts(parts);

        assertEquals(1, lowStock.size());
        assertEquals("P002", lowStock.get(0).getId());
    }

    @Test
    void returnsEmptyList() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Chain", "TVS", 500.0, 50, "ENGINE", "2026-01-01", "NULL", 5));

        assertTrue(lsm.findLSParts(parts).isEmpty());
    }
}
