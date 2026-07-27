package org.example.coursework.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.coursework.*;

import java.util.Locale;

public class CartController {

    @FXML private TextField partIdField;
    @FXML private TextField quantityField;

    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> idColumn;
    @FXML private TableColumn<CartItem, String> nameColumn;
    @FXML private TableColumn<CartItem, Number> quantityColumn;
    @FXML private TableColumn<CartItem, Number> subtotalColumn;

    @FXML private Label summaryLabel;

    private final Cart cart = new Cart();
    private final CheckoutParts checkoutParts = new CheckoutParts(CommContext.manageInventory, CommContext.auditLogger);

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    @FXML
    private void onAddToCart() {
        try {
            Part part = CommContext.manageInventory.findById(partIdField.getText());
            if (part == null) {
                SceneAlerts.showError("Part ID " + partIdField.getText() + " was not found.");
                return;
            }
            int quantity = Validator.validateQuantity(quantityField.getText());
            cart.addItem(part, quantity);
            refreshCartTable();
            partIdField.clear();
            quantityField.clear();
        } catch (IllegalArgumentException e) {
            SceneAlerts.showError(e.getMessage());
        }
    }

    private void refreshCartTable() {
        cartTable.setItems(FXCollections.observableArrayList(cart.getItems()));
    }

    @FXML
    private void onCheckout() {
        try {
            Receipt receipt = checkoutParts.checkout(cart);
            String message = String.format(Locale.ENGLISH,
                    "Sale complete!%nSubtotal: Rs. %.2f%nLine discounts: -Rs. %.2f%nSynergy discount applied: %s%nTotal: Rs. %.2f",
                    receipt.getTotalBeforeDiscounts(), receipt.getLineDiscountsTotal(),
                    receipt.isSynergyDiscountApplied() ? "Yes (10%)" : "No",
                    receipt.getFinalTotal());
            SceneAlerts.showInfo(message);
            summaryLabel.setText("");
            refreshCartTable();
        } catch (IllegalArgumentException e) {
            SceneAlerts.showError(e.getMessage());
        }
    }

    @FXML
    private void onClearCart() {
        cart.clear();
        refreshCartTable();
        summaryLabel.setText("");
    }

    @FXML
    private void onBack(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
