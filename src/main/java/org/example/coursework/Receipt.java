package org.example.coursework;

public class Receipt {
    private final double totalBeforeDiscounts;
    private final double lineDiscountsTotal;
    private final boolean synergyDiscountApplied;
    private final double finalTotal;

    public Receipt(double totalBeforeDiscounts,double lineDiscountsTotal,boolean synergyDiscountApplied,double finalTotal){
        this.totalBeforeDiscounts = totalBeforeDiscounts;
        this.lineDiscountsTotal = lineDiscountsTotal;
        this.synergyDiscountApplied = synergyDiscountApplied;
        this.finalTotal =  finalTotal;
    }

    public double getTotalBeforeDiscounts() {
        return totalBeforeDiscounts;
    }

    public double getLineDiscountsTotal() {
        return lineDiscountsTotal;
    }

    public boolean isSynergyDiscountApplied() {
        return synergyDiscountApplied;
    }

    public double getFinalTotal() {
        return finalTotal;
    }
}
