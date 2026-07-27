package org.example.coursework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ManageInventoryTest {

    @TempDir
    Path tempDir;

    private ManageInventory newManager() {
        return new ManageInventory(tempDir.resolve("inventory.txt").toString(),
                new AuditLogger(tempDir.resolve("audit.txt").toString()));
    }

    @Test
    void addingPart() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        Part found = manager.findById("p001"); // case-insensitive lookup
        assertNotNull(found);
        assertEquals("Chain", found.getName());
    }

    @Test
    void addingDuplicateIdRejected() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        assertThrows(IllegalArgumentException.class, () ->
                manager.addPart(new Part("P001", "Other", "Brand", 100.0, 1, "ENGINE", "2026-01-01", "NULL", 5)));
    }

    @Test
    void updatingPart() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        manager.updatePart("P001",
                new Part("P001", "Chain Sprocket", "TVS", 550.0, 8, "ENGINE", "2026-02-01", "NULL", 6));

        Part updated = manager.findById("P001");
        assertEquals("Chain Sprocket", updated.getName());
        assertEquals(550.0, updated.getPrice());
        assertEquals(8, updated.getQuantity());
        assertEquals(6, updated.getLowStockThreshold());
    }

    @Test
    void updatingUnknownId() {
        ManageInventory manager = newManager();
        assertThrows(IllegalArgumentException.class, () -> manager.updatePart("P999",
                new Part("P999", "Ghost", "None", 1.0, 1, "ENGINE", "2026-01-01", "NULL", 1)));
    }

    @Test
    void deletingPart() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        manager.deletePart("P001");

        assertNull(manager.findById("P001"));
    }

    @Test
    void deletingUnknownId() {
        ManageInventory manager = newManager();
        assertThrows(IllegalArgumentException.class, () -> manager.deletePart("P999"));
    }

    @Test
    void totals() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));
        manager.addPart(new Part("P002", "Brake Pad", "Bajaj", 200.0, 5, "BRAKES", "2026-01-01", "NULL", 5));

        assertEquals(15, manager.getTotalItemCount());
        assertEquals(500.0 * 10 + 200.0 * 5, manager.getTotalInventoryValue());
    }

    @Test
    void reducingStock_belowAvailable_throws() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 5, "ENGINE", "2026-01-01", "NULL", 5));

        assertThrows(IllegalArgumentException.class, () -> manager.reduceStock("P001", 10));
    }

    @Test
    void reducingStock_updatesQuantity() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        manager.reduceStock("P001", 4);

        assertEquals(6, manager.findById("P001").getQuantity());
    }

    @Test
    void dataSurvivesReload_fromSameFile() {
        String filePath = tempDir.resolve("inventory.txt").toString();
        ManageInventory first = new ManageInventory(filePath, new AuditLogger(tempDir.resolve("audit.txt").toString()));
        first.addPart(new Part("P001", "Chain", "TVS", 500.0, 10, "ENGINE", "2026-01-01", "NULL", 5));

        ManageInventory second = new ManageInventory(filePath, new AuditLogger(tempDir.resolve("audit.txt").toString()));
        assertNotNull(second.findById("P001"));
        assertEquals(10, second.findById("P001").getQuantity());
    }
}
