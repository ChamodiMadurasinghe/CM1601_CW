package org.example.coursework;

import java.util.*;

public class LSM {
    public List<Part> findLSParts(List<Part> parts){
        List<Part> lowStock = new ArrayList<>();
        for(Part part :parts){
            if(part.isLowStock()){
                lowStock.add(part);
            }
        }
        return lowStock;
    }

    public void printLSParts(List<Part> parts){
        for(Part part : findLSParts(parts)){
            System.out.println(part.getName() + " (" + part.getId() + ") - Qty: " + part.getQuantity() + " (Threshold: " + part.getLowStockThreshold() + ")");

        }
    }
}