package org.example.coursework;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Validator {
    private static final String empty = "NULL";

    public static String validateId(String initial, List<String> existingIds){
        if (initial == null || initial.trim().isEmpty()){
            throw new IllegalArgumentException("Part ID cannot be empty.");
        }
        String id = initial.trim().toUpperCase();
        if(!id.matches("[P]\\d{3}")){
            throw new IllegalArgumentException("Part ID eg:p001 / P001");
        }
        if (existingIds != null){
            for (String existing : existingIds){
                if (existing.equalsIgnoreCase(id)){
                    throw new IllegalArgumentException("Part ID " + id + " already exists.");
                }
            }
        }
        return id;
    }

    public String validateName (String initial){
        if (initial == null || initial.trim().isEmpty()){
            throw new IllegalArgumentException("Part name cannot be empty.");
        }
        return initial.trim();
    }

    public String validateBrand (String initial){
        if (initial == null || initial.trim().isEmpty()){
            return empty;
        }
        return initial.trim();
    }

    public static double validatePrice (String initial){
        if (initial == null || initial.trim().isEmpty()){
            throw new IllegalArgumentException("Price cannot be empty.");
        }
        String cleaned = initial.trim().replace("Rs.","").replace("Rs","").trim();
        double price;
        try {
            price = Double.parseDouble(cleaned);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Price must be valid number.");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        return price;
    }

    public static int validateQuantity(String initial){
        if (initial == null || initial.trim().isEmpty()){
            throw new IllegalArgumentException("Quantity cannot be empty.");
        }
        int quantity;
        try{
            quantity = Integer.parseInt(initial.trim());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Quantity must be a whole number.");
        }
        if (quantity <0){
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        return quantity;
    }

    public static String validateCategory(String initial){
        if(initial == null || initial.trim().isEmpty()){
            return empty;
        }
        return initial.trim().toUpperCase();
    }

    public static String validateDate (String initial){
        if (initial == null || initial.trim().isEmpty()){
            return empty;
        }
        String cleaned = initial.trim().replace("/","-");
        try{
            LocalDate date = LocalDate.parse(cleaned, DateTimeFormatter.ISO_LOCAL_DATE);
            return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (Exception e){
            throw new IllegalArgumentException("Date mmust be in yyyy-MM-dd format.");
        }
    }

    public static int validateThreshold(String initial){
        if(initial == null || initial.trim().isEmpty()){
            return Part.defaultThreshold;
        }
        int threshold;
        try{
            threshold = Integer.parseInt(initial.trim());
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Low stock threshold must be a whole number.");
        }
        if (threshold < 0){
            throw new IllegalArgumentException("Low stock threshold cannot be negative.");
        }
        return threshold;
    }

    public static String validateImageFileName(String initial){
        if (initial == null || initial.trim().isEmpty()){
            return empty;
        }
        return initial.trim();
    }
}
