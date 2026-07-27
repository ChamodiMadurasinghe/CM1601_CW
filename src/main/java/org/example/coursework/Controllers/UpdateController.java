package org.example.coursework.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.coursework.*;

public class UpdateController {

    @FXML private TextField searchIdField;
    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField categoryField;
    @FXML private TextField dateField;
    @FXML private TextField thresholdField;

    private Part currentPart;

    @FXML
    private void onSearch() {
        String id = searchIdField.getText();
        currentPart = CommContext.manageInventory.findById(id);
        if (currentPart == null) {
            SceneAlerts.showError("Part ID " + id + " was not found.");
            return;
        }
        nameField.setText(currentPart.getName());
        brandField.setText(currentPart.getBrand());
        priceField.setText(String.valueOf(currentPart.getPrice()));
        quantityField.setText(String.valueOf(currentPart.getQuantity()));
        categoryField.setText(currentPart.getCategory());
        dateField.setText(currentPart.getDate());
        thresholdField.setText(String.valueOf(currentPart.getLowStockThreshold()));
    }

    @FXML
    private void onUpdate() {
        if (currentPart == null) {
            SceneAlerts.showError("Search for a part ID first.");
            return;
        }
        try {
            String name = Validator.validateName(nameField.getText());
            String brand = Validator.validateBrand(brandField.getText());
            double price = Validator.validatePrice(priceField.getText());
            int quantity = Validator.validateQuantity(quantityField.getText());
            String category = Validator.validateCategory(categoryField.getText());
            String date = Validator.validateDate(dateField.getText());
            int threshold = Validator.validateThreshold(thresholdField.getText());

            Part updated = new Part(currentPart.getId(), name, brand, price, quantity,
                    category, date, currentPart.getImageFile(), threshold);
            CommContext.manageInventory.updatePart(currentPart.getId(), updated);

            SceneAlerts.showInfo("Part " + currentPart.getId() + " updated successfully.");
        } catch (IllegalArgumentException e) {
            SceneAlerts.showError(e.getMessage());
        }
    }

    @FXML
    private void onClear() {
        searchIdField.clear();
        nameField.clear();
        brandField.clear();
        priceField.clear();
        quantityField.clear();
        categoryField.clear();
        dateField.clear();
        thresholdField.clear();
        currentPart = null;
    }

    @FXML
    private void onCancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
