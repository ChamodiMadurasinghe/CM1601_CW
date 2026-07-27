package org.example.coursework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerDataCleanerTest {

    @TempDir
    Path tempDir;

    @Test
    void cleanedDealerRecords() throws IOException {
        Path legacy = tempDir.resolve("legacy.txt");
        Path clean = tempDir.resolve("clean.txt");

        Files.write(legacy, List.of(
                "D001,Malabe Auto Spares,0771234567,malabe",
                "D002|Kandy Tuk Parts|0772345678|kandy",
                "D004,Negombo Spare Hub,,negombo"
        ));

        new DealerDataCleaner(legacy.toString()).cleanLegacy(clean.toString());

        List<String> lines = Files.readAllLines(clean);
        assertEquals(3, lines.size());

        String[] first = lines.get(0).split("\\|", -1);
        assertEquals("D001", first[0]);
        assertEquals("Malabe Auto Spares", first[1]);
        assertEquals("0771234567", first[2]);
        assertEquals("MALABE", first[3]);

        String[] third = lines.get(2).split("\\|", -1);
        assertEquals("NULL", third[2]);
    }
}
