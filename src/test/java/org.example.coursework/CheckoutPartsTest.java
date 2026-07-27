package org.example.coursework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutPartsTest {

    @TempDir
    Path tempDir;

    private ManageInventory newManager() {
        return new ManageInventory(tempDir.resolve("inventory.txt").toString(),
                new AuditLogger(tempDir.resolve("audit.txt").toString()));
    }

    @Test
    void emptyCart() {
        ManageInventory manager = newManager();
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        assertThrows(IllegalArgumentException.class, () -> CheckoutParts.checkout(new Cart()));
    }

    @Test
    void getsFivePercentLineDiscount() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Spark Plug", "NGK", 100.0, 20, "ENGINE", "2026-01-01", "NULL", 5));
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        Cart cart = new Cart();
        cart.addItem(manager.findById("P001"), 4); // 4 x 100 = 400, 5% off = 20 discount

        Receipt receipt = checkoutParts.checkout(cart);

        assertEquals(400.0, receipt.getTotalBeforeDiscounts());
        assertEquals(20.0, receipt.getLineDiscountsTotal());
        assertFalse(receipt.isSynergyDiscountApplied());
        assertEquals(380.0, receipt.getFinalTotal());
    }

    @Test
    void getsNoLineDiscount() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Spark Plug", "NGK", 100.0, 20, "ENGINE", "2026-01-01", "NULL", 5));
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        Cart cart = new Cart();
        cart.addItem(manager.findById("P001"), 2);

        Receipt receipt = CheckoutParts.checkout(cart);

        assertEquals(0.0, receipt.getLineDiscountsTotal());
        assertEquals(200.0, receipt.getFinalTotal());
    }

    @Test
    void getsTenPercentSynergyDiscount() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Piston", "Hero", 1000.0, 10, "ENGINE", "2026-01-01", "NULL", 5));
        manager.addPart(new Part("P002", "Battery", "Exide", 1000.0, 10, "ELECTRICAL", "2026-01-01", "NULL", 5));
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        Cart cart = new Cart();
        cart.addItem(manager.findById("P001"), 1); // 1000, no line discount
        cart.addItem(manager.findById("P002"), 1); // 1000, no line discount

        Receipt receipt = CheckoutParts.checkout(cart);

        assertTrue(receipt.isSynergyDiscountApplied());
        assertEquals(1800.0, receipt.getFinalTotal());
    }

    @Test
    void noSynergyDiscount() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Piston", "Hero", 1000.0, 10, "ENGINE", "2026-01-01", "NULL", 5));
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        Cart cart = new Cart();
        cart.addItem(manager.findById("P001"), 1);

        Receipt receipt = checkoutParts.checkout(cart);

        assertFalse(receipt.isSynergyDiscountApplied());
        assertEquals(1000.0, receipt.getFinalTotal());
    }

    @Test
    void deductsStockFromInventory() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Piston", "Hero", 1000.0, 10, "ENGINE", "2026-01-01", "NULL", 5));
        CheckoutParts checkoutParts = new CheckoutParts(manager, new AuditLogger(tempDir.resolve("audit.txt").toString()));

        Cart cart = new Cart();
        cart.addItem(manager.findById("P001"), 4);
        checkoutParts.checkout(cart);

        assertEquals(6, manager.findById("P001").getQuantity());
    }

    @Test
    void cannotAddMoreThanAvailableStock() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Piston", "Hero", 1000.0, 3, "ENGINE", "2026-01-01", "NULL", 5));

        Cart cart = new Cart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(manager.findById("P001"), 5));
    }

    @Test
    void cannotAddZeroOrNegativeQuantity() {
        ManageInventory manager = newManager();
        manager.addPart(new Part("P001", "Piston", "Hero", 1000.0, 3, "ENGINE", "2026-01-01", "NULL", 5));

        Cart cart = new Cart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(manager.findById("P001"), 0));
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(manager.findById("P001"), -1));
    }
}
