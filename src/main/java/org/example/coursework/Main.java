package org.example.coursework;

public class Main {
    public static void main (String[] args) {
        LSM sReader = new LSM("inventoy_legacy.txt");
        sReader.lowStock(5);


        dataCleaner iReader = new dataCleaner("inventoy_legacy.txt");
        iReader.cleanInventory("inventory_clean.txt");
    }
}