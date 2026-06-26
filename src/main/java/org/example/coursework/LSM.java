package org.example.coursework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LSM {
    private String fileName;

    public LSM(String fileName){
        this.fileName = fileName;
    }

    public void lowStock(int threshold){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("[,|;]");
                if (fields.length >4) {
                    String qty = fields[4].trim();
                    try {
                        int qtyI = Integer.parseInt(qty);
                        if (qtyI <= threshold){
                            System.out.println(line);
                        }
                    } catch (NumberFormatException e){
                }
            }
        }


    }catch (IOException e) {
            e.printStackTrace();
        }}
    }

