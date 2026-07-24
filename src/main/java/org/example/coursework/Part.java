package org.example.coursework;

public class Part {
    private String id;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String date;
    private String imageFile;

    public Part(String id,String name,String brand,double price,int quantity,String category,String date,String imageFile){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.date = date;
        this.imageFile = imageFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

