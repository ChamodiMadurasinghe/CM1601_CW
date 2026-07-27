package org.example.coursework;


import java.io.*;
import java.util.*;

public class ManageInventory {
    private final String initialFile;
    private final List<Part> parts = new ArrayList<>();
    private final AuditLogger auditLogger;


    public ManageInventory(String initialFile) {this(initialFile,new AuditLogger());}

    public ManageInventory(String initialFile, AuditLogger auditLogger) {
        this.initialFile = initialFile;
        this.auditLogger = auditLogger;
        load();
    }

    private void load(){
        parts.clear();
        File file = new File(initialFile);
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;
                String[] f = line.split(",",-1);
                if(f.length <8) continue;

                Part p = new Part();
                p.setId(f[0].trim());
                p.setName(f[1].trim());
                p.setBrand(f[2].trim());
                p.setPrice(safeDouble(f[3].trim()));
                p.setQuantity(safeInt(f[4].trim()));
                p.setCategory(f[5].trim());
                p.setDate(f[6].trim());
                p.setImageFile(f[7].trim());
                p.setLowStockThreshold(f.length >8? safeInt(f[8].trim()) : Part.defaultThreshold);
                parts.add(p);
            }
        }catch (IOException e){
            throw new RuntimeException("Could not load inventory file: " + initialFile,e);
        }
    }

    private void saveAll(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(initialFile)) ){
            for (Part p: parts){
                writer.println(p.toString());
            }
        }catch (IOException e){
            throw new RuntimeException("Error saving inventory file: " + initialFile,e);
        }
    }

    private double safeDouble(String value){
        try{
            return Double.parseDouble(value);
        }catch (NumberFormatException e){
            return 0.0;
        }
    }

    private int safeInt(String value){
        try{
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public List<Part> getAllPartsSorted(){
        List<Part> copy = new ArrayList<>(parts);
        Sortings.byCategoryAndId(copy);
        return copy;
    }

    public List<String> getAllIds(){
        List<String> ids = new ArrayList<>();
        for (Part p : parts){
            ids.add(p.getId());
        }
        return ids;
    }

    public Part findById(String id){
        if (id == null) return null;
        for (Part p : parts){
            if(p.getId().equalsIgnoreCase(id.trim())){
                return p;
            }
        }
        return null;
    }

    public boolean idExists(String id){ return findById(id) != null;}

    public int getTotalItemCount(){
        int total = 0;
        for (Part p: parts){
            total += p.getQuantity();
        }
        return total;
    }

    public double getTotalInventoryValue(){
        double total = 0;
        for(Part p: parts){
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    public void addPart (Part part){
        if (idExists(part.getId())){
            throw new IllegalArgumentException("Part ID " +part.getId() + " already exists.");
        }
        parts.add(part);
        saveAll();
        auditLogger.log("ADD",part.getId(),part.getQuantity());
    }

    public void updatePart(String id, Part updated){
        Part existing = findById(id);
        if (existing == null){
            throw new IllegalArgumentException("Part Id " + id + " was not found.");
        }
        existing.setName(updated.getName());
        existing.setBrand(updated.getBrand());
        existing.setPrice(updated.getPrice());
        existing.setQuantity(updated.getQuantity());
        existing.setCategory(updated.getCategory());
        existing.setDate(updated.getDate());
        existing.setImageFile(updated.getImageFile());
        existing.setLowStockThreshold(updated.getLowStockThreshold());
        saveAll();
        auditLogger.log("UPDATE",existing.getId(),existing.getQuantity());
    }

    public void deletePart(String id){
        Part existing = findById(id);
        if (existing == null){
            throw new IllegalArgumentException("Part ID " + id + " was not found.");
        }
        parts.remove(existing);
        saveAll();
        auditLogger.log("DELETE",existing.getId(),0);
    }

    public void reduceStock(String id, int quantitySold){
        Part existing = findById(id);
        if (existing == null){
            throw new IllegalArgumentException("Part ID " + id + " was not found.");
        }
        if (quantitySold > existing.getQuantity()){
            throw new IllegalArgumentException("Not enough stock of " + existing.getName() + " to complete this sale.");
        }
        existing.setQuantity(existing.getQuantity() - quantitySold);
        saveAll();
    }
}

















