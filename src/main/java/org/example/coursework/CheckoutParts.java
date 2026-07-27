package org.example.coursework;

import java.util.List;

public class CheckoutParts {
    private final ManageInventory manageInventory;
    private final AuditLogger auditLogger;

    public CheckoutParts(ManageInventory manageInventory,AuditLogger auditLogger){
        this.manageInventory = manageInventory;
        this.auditLogger = auditLogger;
    }

    public Receipt checkout(Cart cart){
        if(cart == null || cart.isEmpty()){
            throw new IllegalArgumentException("Cart is empty. Add at least one item before checkout.");
        }

        List<CartItem> items = cart.getItems();

        for (CartItem item :items){
            if(item.getQuantity() > item.getPart().getQuantity()){
                throw new IllegalArgumentException("Not enough stock in " + item.getPart().getName() + ".");
            }
        }

        double totalBeforeDiscounts = 0.0;
        double lineDiscountsTotal = 0.0;
        boolean hasEngine = false;
        boolean hasElectrical = false;

        for(CartItem item : items){
            totalBeforeDiscounts += item.getSubtotal();
            lineDiscountsTotal += item.getLineDiscount();

            String category = item.getPart().getCategory();
            if(category != null){
                if(category.equalsIgnoreCase("ENGINE")) hasEngine = true;
                if(category.equalsIgnoreCase("ELECTRICAL")) hasElectrical = true;
            }
        }

        double totalAfterLineDiscounts = totalBeforeDiscounts - lineDiscountsTotal;

        boolean synergyApplied = hasElectrical && hasEngine;
        double finalTotal = totalAfterLineDiscounts;
        if (synergyApplied){
            finalTotal = totalAfterLineDiscounts * 0.90;
        }

        for(CartItem item : items){
            Part part = item.getPart();
            manageInventory.reduceStock(part.getId(),item.getQuantity());
            auditLogger.log("SALE",part.getId(), item.getQuantity());
        }

        cart.clear();
        return new Receipt(totalBeforeDiscounts,lineDiscountsTotal,synergyApplied,finalTotal);
    }
}
