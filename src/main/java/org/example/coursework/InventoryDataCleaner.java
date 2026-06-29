package org.example.coursework;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryDataCleaner {

    private final String initialFile;

    private static final Pattern monthDate = Pattern.compile(
            "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(\\d{1,2}),\\s*(\\d{4})",
            Pattern.CASE_INSENSITIVE);
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().appendPattern("[yyyy-MM-dd]" + "[dd/MM/yyyy]" + "[yyyy/MM/dd]"+
                    "[dd-MM-yyyy]" + "[dd-MMM-yyyy]" + "[yyyy-MMM-dd]" + "[MMM d yyyy]").toFormatter(Locale.ENGLISH);

    private static final String empty = "NULL";

    public InventoryDataCleaner(String initialFile) {
        this.initialFile = initialFile;
    }
    private String dateComma(String line) {
        Matcher m = monthDate.matcher(line);
        return m.replaceAll("$1 $2 $3");
    }

    private String normalizeDate(String iDate) {
        String cleanDate = (iDate == null) ? "" : iDate.trim();
        if (cleanDate.isEmpty()) {
            return empty;
        }
        try {
            LocalDate cDate = LocalDate.parse(cleanDate, DATE_FORMATTER);
            return cDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return empty;
        }
    }

    public void cleanInventory(String finalFile) {
        int cleanedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(initialFile));
             PrintWriter writer = new PrintWriter(new FileWriter(finalFile))) {

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }
                String nLine = dateComma(line);
                String[] fields = nLine.split("[,|;]");

                if (fields.length < 5) {
                    continue;
                }

                try {
                    String id = fields[0].trim().toUpperCase();
                    if(id.isEmpty()){
                        id = empty;
                    }
                    String name = fields[1].trim();
                    if(name.isEmpty()){
                        name = empty;
                    }

                    String brand = fields[2].trim();
                    if (brand.isEmpty()) {
                        brand = empty;
                    }

                    String iPrice = fields[3].trim()
                            .replace("Rs.", "")
                            .replace("Rs", "")
                            .trim();
                    double price = Double.parseDouble(iPrice);
                    if(price == 0.00) {
                        price = Double.parseDouble(empty);
                    }

                    String qty = fields[4].trim();
                    int quantity = Integer.parseInt(qty);
                    if(qty.isEmpty()) {
                        quantity = Integer.parseInt("0");
                    }

                    String category = empty;
                    if (fields.length > 5 && !fields[5].trim().isEmpty()) {
                        category = fields[5].trim().toUpperCase();
                    }

                    String iDate = (fields.length > 6) ? fields[6].trim() : "";
                    String cleanDate = normalizeDate(iDate);

                    String imageFile = empty;
                    String lastField = fields[fields.length - 1].trim();
                    if (lastField.endsWith(".jpg") || lastField.endsWith(".jpeg") || lastField.endsWith(".png") || lastField.endsWith(".svg")) {
                        imageFile = lastField;
                    }

                    writer.printf("%s,%s,%s,%.2f,%d,%s,%s,%s%n",
                            id, name, brand, price, quantity, category, cleanDate, imageFile);
                    cleanedCount++;

                } catch (NumberFormatException e) {
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }

            System.out.println("Success! Cleaned " + cleanedCount + " records into " + finalFile);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find input file: " + initialFile, e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while cleaning inventory file", e);
        }
    }
}
