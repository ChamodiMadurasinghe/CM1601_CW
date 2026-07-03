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

    public static class Part {
        private String id;
        private String name;
        private String brand;
        private double price;
        private int quantity;
        private String category;
        private String date;
        private String imageFile;

        public Part(String id,String name,String brand,double price,int quantity,String category,String date,String imageFile){
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
        public String addPart(){

        String id = validateId();
        if(id == null) {
            return "Part Id cannot be empty.";
        }

        String name = validateName();
            if (name.isEmpty()) {
                return "Part name cannot be empty.";
            }

       /*
        String brand = ;
        double price = ;
        int quantity =;
        String category =;
        String date =;
        String imageFile=;
        */
            return "Part " + id + " , " + name + " added to the " + initialFile + " successfully.";
        }

    private String validateId() {
            while(true){
                System.out.print("Enter part ID (ex:P001/p001): ");
                String id = scanner.nextLine().toUpperCase();

                if (!id.matches("P\\d{3}")) {
                    System.out.println("Please enter the ID in correct format.");
                    continue;
                }
                if (idExists(id)) {
                    System.out.println("Part ID is already exists in inventory.");
                    continue;
                }
                if (id==null){
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
            System.out.print("Enter part name: ");
            String name = scanner.nextLine().trim();
            return name;
    }
}













