package org.example.coursework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LSM {
    public static void main(String[] args){
        try {
            File myfile = new File("inventoy_legacy.txt");
            FileReader fileReader = new FileReader(myfile);

            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("[,|;]");
                if (fields.length >4) {
                    String qty = fields[4].trim();
                    try {
                        int qtyI = Integer.parseInt(qty);
                        if (qtyI <= 5){
                            System.out.println(line);
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
