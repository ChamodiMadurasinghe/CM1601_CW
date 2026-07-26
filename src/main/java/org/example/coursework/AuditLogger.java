package org.example.coursework;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {

    private final String logFile;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AuditLogger(String logFile){this.logFile = logFile;}

    public AuditLogger(){this("audit_log.txt");}

    public void log(String action, String itemCode,int quantity){
        String  timestamp = LocalDateTime.now().format(timeFormat);
        String line = timestamp + " | " + action + " | " + itemCode +" | qty =" +quantity;

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile,true))){
            writer.println(line);
        }catch (IOException e) {
            System.err.println("Could not write to audit log: " + e.getMessage());
        }
    }
}
