package org.example.coursework;

import java.io.*;
import java.util.*;

public class ManageInventory {
    private String initialFile;
    private Scanner scanner;


    public ManageInventory(String initialFile){
        this.initialFile = initialFile;
        this.scanner = new Scanner(System.in);
    }

    public class Part {
        private String id;
        private String name;
        private String brand;
        private double price;
        private int quantity;
        private String category;
        private String date;
        private String imageFile;
    }
        public void addPart(){
        String id = validateId();

        if(id == null){
            return ;
        }

        String name = validateName();

        /*
        String brand = ;
        double price = ;
        int quantity =;
        String category =;
        String date =;
        String imageFile=;
        */
    }

    private String validateId() {
            while(true){
                System.out.print("Enter part ID (ex:P001): ");
                String id = scanner.nextLine().toUpperCase();

                if (!id.matches("P\\d{3}")) {
                    System.out.println("Please enter the ID in correct format.");
                    continue;
                }
                if (idExists(id)) {
                    System.out.println("Code already exists in inventory.");
                    continue;
                }
                return id;

                }
            }

    private boolean idExists(String id) {
        File file = new File(initialFile);
        if (!file.exists()) {
            return false;
            }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split(",", -1);
                if (fields.length == 0) {
                    continue;
                }
                String existingId = fields[0].trim();
                if (existingId.equalsIgnoreCase(id)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Error reading inventory file while checking ID: " + initialFile, e);
        }

        return false;
    }

    private String validateName(){
        while(true){
            System.out.println("Enter part name: ");
        }
    }


















}













/*

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ManageInventory {

    public static class Part {
        private String id;
        private String name;
        private String brand;
        private double price;
        private int quantity;
        private String category;
        private String date;
        private String imageFile;

        public Part(String id, String name, String brand, double price,
                    int quantity, String category, String date, String imageFile) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
            this.date = date;
            this.imageFile = imageFile;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public String getCategory() { return category; }
        public String getDate() { return date; }
        public String getImageFile() { return imageFile; }

        public String toCsvLine() {
            return String.format("%s,%s,%s,%.2f,%d,%s,%s,%s",
                    id, name, brand, price, quantity, category, date, imageFile);
        }

        @Override
        public String toString() {
            return toCsvLine();
        }
    }

     public static class Result {
        private final boolean success;
        private final String message;

        public Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }

        @Override
        public String toString() {
            return (success ? "[OK] " : "[FAILED] ") + message;
        }
    }

    private final Map<String, Part> items = new LinkedHashMap<>();

     public Result addPart(String id, String name, String brand,
                          String priceText, String quantityText,
                          String category, String date, String imageFile) {

        List<String> errors = new ArrayList<>();

        if (isBlank(id)) errors.add("Part ID is required.");
        if (isBlank(name)) errors.add("Part name is required.");

        Double price = parsePrice(priceText, errors);
        Integer quantity = parseQuantity(quantityText, errors);

        if (!errors.isEmpty()) {
            return new Result(false, "Could not add part - " + String.join(" ", errors));
        }

        String cleanId = id.trim();
        if (items.containsKey(cleanId)) {
            return new Result(false, "Could not add part - a part with ID '"
                    + cleanId + "' already exists. Use update instead, or choose a different ID.");
        }

        Part part = new Part(
                cleanId,
                name.trim(),
                isBlank(brand) ? "NULL" : brand.trim(),
                price,
                quantity,
                isBlank(category) ? "NULL" : category.trim().toUpperCase(),
                isBlank(date) ? "NULL" : date.trim(),
                isBlank(imageFile) ? "NULL" : imageFile.trim());

        items.put(cleanId, part);

        return new Result(true, "Part '" + cleanId + "' (" + part.getName() + ") added successfully.");
    }


    public Result updatePart(String id, String name, String brand,
                             String priceText, String quantityText,
                             String category, String date, String imageFile) {

        if (isBlank(id)) {
            return new Result(false, "Could not update part - Part ID is required.");
        }

        String cleanId = id.trim();
        Part existing = items.get(cleanId);
        if (existing == null) {
            return new Result(false, "Could not update part - no part found with ID '" + cleanId + "'.");
        }

        List<String> errors = new ArrayList<>();
        Double price = isBlank(priceText) ? null : parsePrice(priceText, errors);
        Integer quantity = isBlank(quantityText) ? null : parseQuantity(quantityText, errors);

        if (!errors.isEmpty()) {
            return new Result(false, "Could not update part - " + String.join(" ", errors));
        }

        if (!isBlank(name)) existing.name = name.trim();
        if (!isBlank(brand)) existing.brand = brand.trim();
        if (price != null) existing.price = price;
        if (quantity != null) existing.quantity = quantity;
        if (!isBlank(category)) existing.category = category.trim().toUpperCase();
        if (!isBlank(date)) existing.date = date.trim();
        if (!isBlank(imageFile)) existing.imageFile = imageFile.trim();

        return new Result(true, "Part '" + cleanId + "' updated successfully.");
    }

    public Result deletePart(String id) {
        if (isBlank(id)) {
            return new Result(false, "Could not delete part - Part ID is required.");
        }

        String cleanId = id.trim();
        Part removed = items.remove(cleanId);

        if (removed == null) {
            return new Result(false, "Could not delete part - no part found with ID '" + cleanId + "'.");
        }

        return new Result(true, "Part '" + cleanId + "' (" + removed.getName() + ") deleted successfully.");
    }

    public List<Part> getAllParts() {
        return new ArrayList<>(items.values());
    }

    public Part findById(String id) {
        return id == null ? null : items.get(id.trim());
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] f = line.split(",", -1);
                if (f.length < 8) {
                    System.err.println("Skipped malformed line on load: " + line);
                    continue;
                }
                try {
                    Part part = new Part(
                            f[0].trim(), f[1].trim(), f[2].trim(),
                            Double.parseDouble(f[3].trim()),
                            Integer.parseInt(f[4].trim()),
                            f[5].trim(), f[6].trim(), f[7].trim());
                    items.put(part.getId(), part);
                } catch (NumberFormatException e) {
                    System.err.println("Skipped line with bad number on load: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Inventory file not found, starting empty: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading inventory file: " + filePath, e);
        }
    }

    public void saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Part part : items.values()) {
                writer.println(part.toCsvLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing inventory file: " + filePath, e);
        }
    }

    // -----------------------------------------------------------
    // Private validation helpers (shared by add and update)
    // -----------------------------------------------------------
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private Double parsePrice(String priceText, List<String> errors) {
        if (isBlank(priceText)) {
            errors.add("Price is required.");
            return null;
        }
        try {
            String cleaned = priceText.trim().replace("Rs.", "").replace("Rs", "").trim();
            double price = Double.parseDouble(cleaned);
            if (price < 0) {
                errors.add("Price cannot be negative.");
                return null;
            }
            return price;
        } catch (NumberFormatException e) {
            errors.add("Price must be a valid number.");
            return null;
        }
    }

    private Integer parseQuantity(String quantityText, List<String> errors) {
        if (isBlank(quantityText)) {
            errors.add("Quantity is required.");
            return null;
        }
        try {
            int quantity = Integer.parseInt(quantityText.trim());
            if (quantity < 0) {
                errors.add("Quantity cannot be negative.");
                return null;
            }
            return quantity;
        } catch (NumberFormatException e) {
            errors.add("Quantity must be a whole number.");
            return null;
        }
    }

    // =============================================================
    // Interactive console menu - asks the user to add/update/delete
    // =============================================================
    public static void main(String[] args) {
        ManageInventory manager = new ManageInventory();
        Scanner scanner = new Scanner(System.in);

        final String INVENTORY_FILE = "inventoy_legacy.txt";
        manager.loadFromFile(INVENTORY_FILE);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAdd(manager, scanner);
                    break;
                case "2":
                    handleUpdate(manager, scanner);
                    break;
                case "3":
                    handleDelete(manager, scanner);
                    break;
                case "4":
                    handleListAll(manager);
                    break;
                case "5":
                    manager.saveToFile(INVENTORY_FILE);
                    System.out.println("Inventory saved to " + INVENTORY_FILE + ". Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Inventory Menu =====");
        System.out.println("1. Add a new part");
        System.out.println("2. Update an existing part");
        System.out.println("3. Delete a part");
        System.out.println("4. List all parts");
        System.out.println("5. Save and exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleAdd(ManageInventory manager, Scanner scanner) {
        System.out.println("--- Add New Part ---");

        System.out.print("Part ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Brand (leave blank for NULL): ");
        String brand = scanner.nextLine();

        System.out.print("Price (e.g. 1500.00): ");
        String price = scanner.nextLine();

        System.out.print("Quantity: ");
        String quantity = scanner.nextLine();

        System.out.print("Category (leave blank for NULL): ");
        String category = scanner.nextLine();

        System.out.print("Date (e.g. 2024-03-15, leave blank for NULL): ");
        String date = scanner.nextLine();

        System.out.print("Image filename (leave blank for NULL): ");
        String image = scanner.nextLine();

        Result result = manager.addPart(id, name, brand, price, quantity, category, date, image);
        System.out.println(result);
    }

    private static void handleUpdate(ManageInventory manager, Scanner scanner) {
        System.out.println("--- Update Part ---");

        System.out.print("Part ID to update: ");
        String id = scanner.nextLine();

        Part existing = manager.findById(id);
        if (existing == null) {
            System.out.println("[FAILED] No part found with ID '" + id.trim() + "'.");
            return;
        }

        System.out.println("Current values: " + existing);
        System.out.println("Press Enter on any field to keep its current value.");

        System.out.print("New name [" + existing.getName() + "]: ");
        String name = scanner.nextLine();

        System.out.print("New brand [" + existing.getBrand() + "]: ");
        String brand = scanner.nextLine();

        System.out.print("New price [" + existing.getPrice() + "]: ");
        String price = scanner.nextLine();

        System.out.print("New quantity [" + existing.getQuantity() + "]: ");
        String quantity = scanner.nextLine();

        System.out.print("New category [" + existing.getCategory() + "]: ");
        String category = scanner.nextLine();

        System.out.print("New date [" + existing.getDate() + "]: ");
        String date = scanner.nextLine();

        System.out.print("New image filename [" + existing.getImageFile() + "]: ");
        String image = scanner.nextLine();

        Result result = manager.updatePart(id, name, brand, price, quantity, category, date, image);
        System.out.println(result);
    }

    private static void handleDelete(ManageInventory manager, Scanner scanner) {
        System.out.println("--- Delete Part ---");
        System.out.print("Part ID to delete: ");
        String id = scanner.nextLine();

        Result result = manager.deletePart(id);
        System.out.println(result);
    }

    private static void handleListAll(ManageInventory manager) {
        System.out.println("--- All Parts ---");
        List<Part> all = manager.getAllParts();
        if (all.isEmpty()) {
            System.out.println("(Inventory is empty.)");
            return;
        }
        for (Part part : all) {
            System.out.println("   " + part);
        }
    }
}
*/