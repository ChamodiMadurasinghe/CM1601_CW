package org.example.coursework;

public class Main {
    public static void main (String[] args) {
        InventoryDataCleaner iReader = new InventoryDataCleaner("inventoy_legacy.txt");
        iReader.cleanInventory("inventory_clean.txt");

        DealerDataCleaner dReader = new DealerDataCleaner("dealers_legacy.txt");
        dReader.cleanLegacy("dealer_clean.txt");

        LSM sReader = new LSM("inventory_clean.txt");
        sReader.lowStock(5);

        ManageInventory CRUD = new ManageInventory("inventory_clean.txt");
        CRUD.addPart();


    }
}