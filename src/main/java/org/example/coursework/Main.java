package org.example.coursework;

public class Main {
    public static void main (String[] args) {
        LSM sReader = new LSM("inventoy_legacy.txt");
        sReader.lowStock(5);

        inventoryDataCleaner iReader = new inventoryDataCleaner("inventoy_legacy.txt");
        iReader.cleanInventory("inventory_clean.txt");

        dealerDataCleaner dReader = new dealerDataCleaner("dealers_legacy.txt");
        dReader.cleanLegacy("dealer_clean.txt");
    }
}