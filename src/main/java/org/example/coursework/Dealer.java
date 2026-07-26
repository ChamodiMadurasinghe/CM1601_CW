package org.example.coursework;

public class Dealer {
    private final String id;
    private final String name;
    private final String contactNumber;
    private final String location;

    public Dealer(String id,String name,String  contactNumber,String location){
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getContactNumber(){
        return contactNumber;
    }

    public String getLocation(){
        return location;
    }

    @Override
    public String toString(){
        return id + " | " + name + " | " + contactNumber + " | " + location;
    }
}
