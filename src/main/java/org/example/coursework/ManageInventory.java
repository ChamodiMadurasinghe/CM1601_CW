package org.example.coursework;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ManageInventory {
    private String initialFile;
    private Scanner scanner;


    public ManageInventory(String initialFile) {
        this.initialFile = initialFile;
        this.scanner = new Scanner(System.in);
    }

    public static class Part {
        private String id;
        private String name;
        private String brand;
        private double price;
        private int quantity;
        private String category;
        private String date;
        private String imageFile;

        public Part(String id, String name, String brand, double price, int quantity, String category, String date, String imageFile) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
            this.date = date;
            this.imageFile = imageFile;
        }
        /*
        public String getId(){
            return id;
        }
        public String getName(){
            return name;
        }
        public String getBrand(){
            return brand;
        }
        public double getprice(){
            return price;
        }
        public int getquantity(){
            return quantity;
        }
        public String getCategory(){
            return category;
        }
        public String getDate(){
            return date;
        }
        public String getImageFile(){
            return imageFile;
        }
        public String toCsvFile() {
            return String.format("%s,%s,%s,%.2f,%d,%s,%s,%s",
                    id, name, brand, price, quantity, category, date, imageFile);
        }
*/


    }

    public String addPart() {

        String id = validateId();
        String name = validateName();
        String brand = validateBrand();
        String price = validatePrice();
        String quantity = validateQty();
        String category = validateCategory();
        String date = validateDate();
        String imageFile = validateImgFile();

        return "Part " + id + " , " + name + " added to the " + initialFile + " successfully.";
    }

    private String validateId() {
        while (true) {
            System.out.print("Enter part ID (ex:P001/p001): ");
            String id = scanner.nextLine().toUpperCase();

            if (id.isEmpty()) {
                System.out.println("Part ID cannot be empty.");
                continue;
            }

            if (!id.matches("P\\d{3}")) {
                System.out.println("Please enter the ID in correct format.");
                continue;
            }
            if (idExists(id)) {
                System.out.println("Part ID is already exists in inventory.");
                continue;
            }
            if (id == null) {
                System.out.println("Part ID cannot be empty.");
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

    private String validateName() {
        while (true) {
            System.out.print("Enter part name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Part name cannot be empty.");
                continue;
            }
            return name;
        }
    }

    private String validateBrand() {
        System.out.print("Enter part brand: ");
        String brand = scanner.nextLine().trim();

        if (brand.isEmpty()) {
            System.out.println("NULL");
        }
        return brand;
    }

    private String validatePrice() {
        while (true) {
            System.out.print("Enter part price (0.00): ");
            String price = scanner.nextLine();
            if (price == null || price.trim().isEmpty()) {
                System.out.println("NULL");
                return null;
            }
            try {
                double iprice = Double.parseDouble(price);
                if (iprice < 0.00) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }
                return price;


            } catch (NumberFormatException e) {
                System.out.println("Invalid input.Please enter a valid price.");

            }
        }
    }

    private String validateQty() {
        while (true) {
            System.out.print("Enter part quantity: ");
            String quantity = scanner.nextLine().trim();

            if (quantity.isEmpty()) {
                System.out.println("NULL");
                return null;
            }
            try {
                int qty = Integer.parseInt(quantity);
                if (qty < 0) {
                    System.out.println("Quantity cannot be negative.");
                    continue;
                }
                return quantity;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.Please enter a valid quantity.");
            }
        }
    }

    private String validateCategory() {
        System.out.print("Enter part category: ");
        String category = scanner.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("NULL");
        }
        return category;
    }

    private String validateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String date = scanner.nextLine().trim();

            if (date.isEmpty()) {
                System.out.println("NULL");
                return null;
            }
            try {
                LocalDate.parse(date, formatter);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in correct format.");

            }
        }
    }

    private String validateImgFile(){
        System.out.print("Enter image file: ");
        String imgFile = scanner.nextLine().trim();

        if (imgFile.isEmpty()) {
            System.out.println("NULL");
        }
        return imgFile;
    }
}














