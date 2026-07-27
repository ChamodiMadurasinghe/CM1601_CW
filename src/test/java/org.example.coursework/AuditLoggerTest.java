package org.example.coursework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditLoggerTest {

    @TempDir
    Path tempDir;

    @Test
    void logEntryWrittenWithActionAndQuantity() throws IOException {
        Path logFile = tempDir.resolve("audit.txt");
        AuditLogger logger = new AuditLogger(logFile.toString());

        logger.log("ADD", "P001", 10);

        List<String> lines = Files.readAllLines(logFile);
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("ADD"));
        assertTrue(lines.get(0).contains("P001"));
        assertTrue(lines.get(0).contains("qty =10"));
    }

    @Test
    void multipleLogEntries_appendRatherThanOverwrite() throws IOException {
        Path logFile = tempDir.resolve("audit.txt");
        AuditLogger logger = new AuditLogger(logFile.toString());

        logger.log("ADD", "P001", 10);
        logger.log("SALE", "P001", 2);
        logger.log("DELETE", "P002", 0);

        List<String> lines = Files.readAllLines(logFile);
        assertEquals(3, lines.size());
        assertTrue(lines.get(0).contains("ADD"));
        assertTrue(lines.get(1).contains("SALE"));
        assertTrue(lines.get(2).contains("DELETE"));
    }
}
