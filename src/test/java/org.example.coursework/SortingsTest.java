package org.example.coursework;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortingsTest {

    private Part part(String id, String category) {
        return new Part(id, "Name-" + id, "Brand", 100.0, 5, category, "2026-01-01", "NULL", 10);
    }

    @Test
    void partsSortedByCategoryThenById() {
        List<Part> parts = new ArrayList<>();
        parts.add(part("P003", "ENGINE"));
        parts.add(part("P001", "BRAKES"));
        parts.add(part("P002", "ENGINE"));
        parts.add(part("P004", "BRAKES"));

        Sortings.byCategoryAndId(parts);

        assertEquals("P001", parts.get(0).getId());
        assertEquals("P004", parts.get(1).getId());
        assertEquals("P002", parts.get(2).getId());
        assertEquals("P003", parts.get(3).getId());
    }

    @Test
    void alreadySortedList() {
        List<Part> parts = new ArrayList<>();
        parts.add(part("P001", "BRAKES"));
        parts.add(part("P002", "ENGINE"));

        Sortings.byCategoryAndId(parts);

        assertEquals("P001", parts.get(0).getId());
        assertEquals("P002", parts.get(1).getId());
    }
}
