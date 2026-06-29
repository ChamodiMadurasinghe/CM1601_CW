package org.example.coursework;

import java.io.*;
import java.io.BufferedReader;

public class DealerDataCleaner {
    private String initialFile;

    public DealerDataCleaner(String initialFile) {
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
                        if(id.isEmpty()){
                            id = "NULL";
                        }
                        String name = fields[1].trim();
                        if(name.isEmpty()){
                            name = "NULL";
                        }

                        String contactNumber = "NULL";
                        if (fields.length > 2 && !fields[2].trim().isEmpty()) {
                            contactNumber = fields[2].trim();
                        }

                        String location = "NULL";
                        if (fields.length > 3 && !fields[3].trim().isEmpty()) {
                            location = fields[3].trim().toUpperCase();
                        }

                        writer.printf("%s|%s|%s|%s%n", id, name, contactNumber, location);
                        cleanedCount++;

                    }catch (NumberFormatException e) {
                    }
                }
            }
            System.out.println("Success! Cleaned " + cleanedCount + " records into " + finalFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: The source file could not be found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
