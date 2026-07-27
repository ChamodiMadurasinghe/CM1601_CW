package org.example.coursework;

import java.util.*;

public class MCSearch {
    public static List<Part> search (List<Part> parts, String category, Double minPrice, Double maxPrice, String keyword) {
        List<Part> results = new ArrayList<>();

        String categoryFilter = (category == null) ? "" : category.trim().toUpperCase();
        String keywordFilter = (keyword == null) ? "" : keyword.trim().toUpperCase();

        for (Part part : parts) {
            if (!categoryFilter.isEmpty()) {
                String partCategory = part.getCategory() == null ? "" : part.getCategory().toUpperCase();
                if (!partCategory.equals(categoryFilter)) {
                    continue;
                }
            }

            if (minPrice != null && part.getPrice() < minPrice) {
                continue;
            }
            if (maxPrice != null && part.getPrice() > maxPrice) {
                continue;
            }

            if (!keywordFilter.isEmpty()) {
                String name = part.getName() == null ? "" : part.getName().toUpperCase();
                String brand = part.getBrand() == null ? "" : part.getBrand().toUpperCase();
                if (!name.contains(keywordFilter) && !brand.contains(keywordFilter)) {
                    continue;
                }
            }
            results.add(part);
        }
        return results;
    }
}
