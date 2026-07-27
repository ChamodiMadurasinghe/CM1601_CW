package org.example.coursework;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CommContext {
    public static final String Inventory_legacy_file = "inventory_legacy.txt";
    public static final String Inventory_clean_file = "inventory_clean.txt";
    public static final String Dealer_legacy_file = "dealers_legacy.txt";
    public static final String Dealer_clean_file = "dealers_clean.txt";

    public static final String Audit_log_file = "audit_log.txt";

    public static AuditLogger auditLogger = new AuditLogger(Audit_log_file);
    public static ManageInventory manageInventory;
    public static RDS dealerSelector;

    public static void initialize(){
        getFileIfMissing(Inventory_legacy_file);
        getFileIfMissing(Dealer_legacy_file);

        if (!new File(Inventory_clean_file).exists()){
            new DealerDataCleaner(Dealer_legacy_file).cleanLegacy(Dealer_clean_file);
        }

        manageInventory = new ManageInventory(Inventory_clean_file,auditLogger);
        dealerSelector = new RDS(Dealer_clean_file);

        System.out.println("Loaded " + manageInventory.getAllPartsSorted().size() + " parts and set up dealer selector.");
    }

    private static void getFileIfMissing(String initialFile){
        File target = new File(initialFile);
        if(target.exists()){
            return;
        }
        try(InputStream in = CommContext.class.getResourceAsStream(initialFile)){
            if (in == null){
                System.out.println("WARINIG: bundeled file " + initialFile  + " was not found.");
                return;
            }
            Files.copy(in,target.toPath());
            System.out.println("Extracted bundles file: " + initialFile);
        }catch (IOException e){
            System.out.println("Could not extract bundled file " + initialFile + ": " + e.getMessage());
        }
    }
}
