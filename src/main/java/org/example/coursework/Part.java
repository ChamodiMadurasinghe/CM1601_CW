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

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getImageFile() {return imageFile;}
    public void setImageFile(String imageFile) {this.imageFile = imageFile;}

    @Override
    public String toString(){
        return id + "," + name + "," + brand + "," + String.format("%.2f", price) + "," + quantity + "," + category +
                "," + date + "," + imageFile;

    }
}

