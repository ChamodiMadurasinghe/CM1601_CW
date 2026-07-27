package org.example.coursework;

public class Main {
    public static void main(String[] args) {
        CommContext.initialize();

        new LSM().printLSParts(CommContext.manageInventory.getAllPartsSorted());
        CommContext.dealerSelector.displayDealers(4);

    }
}
