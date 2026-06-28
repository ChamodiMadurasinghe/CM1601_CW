package org.example.coursework;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.time.*;

public class inventoryDataCleaner {
    private String initialFile;

    public inventoryDataCleaner(String initialFile){
        this.initialFile = initialFile;
    }

    private String normalizeDate(String rawDate){
        String cleanDate = rawDate.trim();
        if(cleanDate.isEmpty()){
            return "NULL";
        }

        DateTimeFormatter newFormatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("["
                + "yyyy-MM-dd" + "]["
                + "dd/MM/yyyy" + "]["
                + "MMM dd, yyyy" + "]["
                + "dd-MMM-yyyy" + "]")).toFormatter(Locale.ENGLISH);

        try{
            LocalDate cDate = LocalDate.parse(cleanDate,newFormatter);
            return cDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        } catch (Exception e){
            return "NULL";
        }
    }

    public void cleanInventory(String finalFile){
        try (BufferedReader reader = new BufferedReader(new FileReader(this.initialFile))) {
            try (PrintWriter writer = new PrintWriter((new FileWriter(finalFile))
            )) {
                String line;
                int cleanedCount = 0;

                while((line =reader.readLine())!=null)

                {
                    if (line.trim().isEmpty()) continue;

                    String[] fields = line.split("[,|;]");

                    if (fields.length >= 5) {
                        try {
                            String id = fields[0].trim();
                            String name = fields[1].trim();
                            String brand = fields[2].trim();
                            if (brand.isEmpty()) brand = "Unknown";

                            String iPrice = fields[3].trim();
                            iPrice = iPrice.replace("Rs.", "").replace("Rs", "").trim();
                            double price = Double.parseDouble(iPrice);

                            String qty = fields[4].trim();
                            int quantity = Integer.parseInt(qty);

                            String category = "Unknown";
                            if (fields.length > 5 && !fields[5].trim().isEmpty()) {
                                category = fields[5].trim().toUpperCase();
                            }

                            String iDate = "";
                            if (fields.length > 6) {
                                iDate = fields[6].trim();
                            }
                            String cleanDate = normalizeDate(iDate);

                            String imageFile = "no_image.png";
                            String lastField = fields[fields.length - 1].trim();
                            if (lastField.endsWith(".jpg") || lastField.endsWith(".jpeg") || lastField.endsWith(".png") || lastField.endsWith(".svg")){
                                imageFile = lastField;
                            }

                            writer.printf("%s|%s|%s|%.2f|%d|%s|%s|%s%n", id, name, brand, price, quantity, category, cleanDate, imageFile);

                            cleanedCount++;

                        } catch (NumberFormatException e) {
                            System.err.println("Skipped unparseable row values: " + line.trim());
                        }
                    }
                }System.out.println("Success! Cleaned and unified "+cleanedCount +" records inside "+finalFile);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }System.err.println("File I/O Error: " + reader.getClass());

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
