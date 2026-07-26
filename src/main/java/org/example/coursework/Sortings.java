package org.example.coursework;

import java.util.*;

public class Sortings {
    public static void byCategoryAndId(List<Part> parts){
        for(int i =1; i < parts.size(); i++){
            Part key = parts.get(i);
            int j = i-1;
            while (j >= 0 && next(parts.get(j), key)){
                parts.set(j +1, parts.get(j));
                j--;
            }
            parts.set(j +1, key);
        }
    }

    private static boolean next(Part existing, Part key){
        int categoryCompare = safe(existing.getCategory()).compareToIgnoreCase(safe(key.getCategory()));
        if (categoryCompare != 0){
            return categoryCompare > 0;
        }
        return safe(existing.getId()).compareToIgnoreCase(safe(key.getId())) >0;
    }
     private static String safe(String value){
        return value == null ? "" : value;
     }
}
