package org.example.coursework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDataCleanerTest {

    @TempDir
    Path tempDir;

    @Test
    void cleanedInventoryRecords() throws IOException {
        Path legacy = tempDir.resolve("legacy.txt");
        Path clean = tempDir.resolve("clean.txt");

        Files.write(legacy, List.of(
                "P001,Chain Sprocket, TVS ,Rs. 3500, 12,engine,2026-01-15,chain.jpg",
                "P002|Brake Pad|Bajaj|Rs.1200|25| brakes |Jan 5, 2026|brakepad.png",
                "P003,Headlight Bulb,,850,,electrical,2026/02/10,"
        ));

        new InventoryDataCleaner(legacy.toString()).cleanInventory(clean.toString());

        List<String> lines = Files.readAllLines(clean);
        assertEquals(3, lines.size());

        String[] first = lines.get(0).split(",", -1);
        assertEquals("P001", first[0]);
        assertEquals("Chain Sprocket", first[1]);
        assertEquals("TVS", first[2]);
        assertEquals("3500.00", first[3]);
        assertEquals("12", first[4]);
        assertEquals("ENGINE", first[5]);

        String[] second = lines.get(1).split(",", -1);
        assertEquals("BRAKES", second[5]);
        assertEquals("2026-01-05", second[6]);

        String[] third = lines.get(2).split(",", -1);
        assertEquals("0", third[4]);
        assertEquals("2026-02-10", third[6]);
    }

    @Test
    void emptyLinesSkipped() throws IOException {
        Path legacy = tempDir.resolve("legacy.txt");
        Path clean = tempDir.resolve("clean.txt");

        Files.write(legacy, List.of("", "   ", "P001,Bolt,Generic,100,50,bodywork,2026-01-01,"));

        new InventoryDataCleaner(legacy.toString()).cleanInventory(clean.toString());

        List<String> lines = Files.readAllLines(clean);
        assertEquals(1, lines.size());
    }
}
