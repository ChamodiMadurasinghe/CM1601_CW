package org.example.coursework;

import java.util.*;

public class Main {
    public static void main (String[] args) {
        InventoryDataCleaner iReader = new InventoryDataCleaner("inventoy_legacy.txt");
        iReader.cleanInventory("inventory_clean.txt");

        DealerDataCleaner dReader = new DealerDataCleaner("dealers_legacy.txt");
        dReader.cleanLegacy("dealer_clean.txt");

        Map<String ,Integer> thresholds = new HashMap<>();
        thresholds.put("ENGINE" ,5);
        thresholds.put("ELECTRICAL" ,10);
        thresholds.put("BODYWORK", 5);
        thresholds.put("BRAKES", 10);

        LSM sReader = new LSM("inventory_clean.txt",5,thresholds);
        sReader.lowStock();

        ManageInventory CRUD = new ManageInventory("inventory_clean.txt");
        CRUD.addPart();

       /* ManageInventory CRUD = new ManageInventory("inventory_clean.txt");
        CRUD.updatePart();*/


    }
}