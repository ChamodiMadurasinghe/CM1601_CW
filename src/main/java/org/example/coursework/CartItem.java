package org.example.coursework;

public class CartItem {
    private final Part part;
    private final int quantity;

    public CartItem(Part part, int quantity){
        this.part = part;
        this.quantity = quantity;
    }

    public Part getPart(){
        return part;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getId(){
        return part.getId();
    }

    public String getName(){
        return part.getName();
    }

    public double getSubtotal(){
        return part.getPrice() * quantity;
    }

    public double getLineDiscount(){
        if(quantity >= 3){
            return getSubtotal() * 0.05;
        }
        return 0.0;
    }
    public double getTotalAfterDiscount(){
        return getSubtotal() - getLineDiscount();
    }
}
