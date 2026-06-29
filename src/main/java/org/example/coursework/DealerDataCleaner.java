package org.example.coursework;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;

public class dealerDataCleaner {
    private String initialFile;

    public dealerDataCleaner(String initialFile) {
        this.initialFile = initialFile;
    }

    public void cleanLegacy(String finalFile) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(this.initialFile));
                PrintWriter writer = new PrintWriter((new FileWriter(finalFile)))) {
            String line;
            int cleanedCount = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split("[,|;]");

                if (fields.length >= 2) {
                    try {
                        String id = fields[0].trim();
                        String name = fields[1].trim();

                        String contactNumber = "NULL";
                        if (fields.length > 2 && !fields[2].trim().isEmpty()) {
                            contactNumber = fields[2].trim();
                        }

                        String location = "Unknown";
                        if (fields.length > 3 && !fields[3].trim().isEmpty()) {
                            location = fields[3].trim().toLowerCase();
                        }

                        writer.printf("%s|%s|%s|%s%n", id, name, contactNumber, location);
                        cleanedCount++;

                    }catch (NumberFormatException e) {
                        System.err.println("Skipped unparseable row values: " + line.trim());
                    }
                }
            }
            System.out.println("Success! Cleaned and unified " + cleanedCount + " records inside " + finalFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: The source file could not be found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
