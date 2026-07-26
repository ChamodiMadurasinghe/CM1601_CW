package org.example.coursework;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem (Part part, int quantity){
        if(part == null){
            throw new IllegalArgumentException("No part selected.");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity can't be negative.");
        }
        if(quantity > part.getQuantity()){
            throw new IllegalArgumentException("Only " + part.getQuantity() + " units of " + part.getName() + " are in stock.");
        }
        items.add(new CartItem(part, quantity));
    }

    public List<CartItem> getItems(){
        return items;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public void clear(){
        items.clear();
    }

}
