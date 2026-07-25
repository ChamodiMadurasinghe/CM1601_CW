package org.example.coursework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LSM {
    private String fileName;
    private  int defaultThreshold;
    private Map<String, Integer> categoryThresholds;

    public LSM(String fileName, int defaultThreshold){
        this.fileName = fileName;
        this.defaultThreshold = defaultThreshold;
        this.categoryThresholds = new HashMap<>();
    }

    public LSM(String fileName, int defaultThreshold, Map<String,Integer> categoryThresholds) {
        this.fileName = fileName;
        this.defaultThreshold = defaultThreshold;
        this.categoryThresholds = (categoryThresholds != null) ? categoryThresholds : new HashMap<>();
    }

    public void setThreshold(String category, int threshold) {
        if (category != null) {
            categoryThresholds.put(category.trim().toUpperCase(), threshold);
        }
    }

    public void lowStock(){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("[,|;]");
                if (fields.length >5) {
                    String qty = fields[4].trim();
                    String category = fields[5].trim().toUpperCase();

                    int threshold = categoryThresholds.getOrDefault(category,defaultThreshold);

                    try {
                        int qtyI = Integer.parseInt(qty);
                        if (qtyI <= threshold){
                            System.out.println(line);
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

