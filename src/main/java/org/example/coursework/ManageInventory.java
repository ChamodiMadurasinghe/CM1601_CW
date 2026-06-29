package org.example.coursework;

import java.io.*;
import java.util.*;

public class ManageInventory {
    private static final String fpath = "inventory_clean.txt";
    private static list<Part> inventory = new list<Part>();
    private String initialFile;
    private Scanner scanner;

    public ManageInventory(String initialFile){
        this.initialFile = initialFile;
        this.scanner = new Scanner(System.in);
    }
    public void AddMethod(){
    }
}
