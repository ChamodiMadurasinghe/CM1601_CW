package org.example.coursework;

import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Part {

    private static final String Images = "images";
    private static final String[] ALLOWED_EXTENSIONS = { ".png", ".jpg", ".jpeg", ".svg" };

    private String id;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String date;
    private String imageFile;

    public Part(){}

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

    private static void ensureImageDirectoryExists(){
       File dir = new File(Images);
       if(!dir.exists()){
           dir.mkdirs();
       }
    }

    public static boolean isValidImageFile(File file){
        if(file == null || !file.isFile()){
            return false;
        }
        String lower = file.getName().toLowerCase();
        for(String ext : ALLOWED_EXTENSIONS){
            if(lower.endsWith(ext)){
                return true;
            }
        }
        return false;
    }
    public static String imageStore(File sourceFile) throws IOException {
        if(!isValidImageFile(sourceFile)){
            throw new IllegalArgumentException(
                    "Not a supported image file (.png, .jpg, .jpeg, .svg only): " +
                    (sourceFile == null ? "null" : sourceFile.getName()));

        }
        ensureImageDirectoryExists();

        String originalName = sourceFile.getName();
        String storedName = System.currentTimeMillis() + "_" + originalName;
        Path target = Paths.get(Images,storedName);

        Files.copy(sourceFile.toPath(),target, StandardCopyOption.REPLACE_EXISTING);
        return storedName;
    }

    public static boolean copyIfExists(String sourceDir, String fileName){
        if(fileName == null || fileName.trim().isEmpty() || fileName.equalsIgnoreCase("NULL")){
            return false;
        }
        File source = new File(sourceDir,fileName.trim());
        if(!isValidImageFile(source)){
            return false;
        }

        ensureImageDirectoryExists();
        try{
            Files.copy(source.toPath(), Paths.get(Images,fileName.trim()),StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public static File resolveImageFile(String fileName){
        if(fileName == null || fileName.trim().isEmpty() || fileName.equalsIgnoreCase("NULL")){
            return null;
        }
        File file = new File(Images,fileName.trim());
        return file.exists() ? file :null;
    }

    public static Image loadImage(String fileName){
        File file = resolveImageFile(fileName);
        if(file == null) return null;

        String lower = file.getName().toLowerCase();
        if(!(lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg"))){
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)){
            return new Image(fis);
        }catch (IOException e){
            return null;
        }
    }

    public static List<String> listAvailableImages(){
        List<String> names = new ArrayList<>();
        File dir = new File(Images);
        if(!dir.exists() || !dir.isDirectory()){
            return names;
        }

        File[] files = dir.listFiles();
        if(files == null){
            return names;
        }

        for(File f: files){
            if (isValidImageFile(f)){
                names.add(f.getName());
            }
        }
        return names;
    }
}

