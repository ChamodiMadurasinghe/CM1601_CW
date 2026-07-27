package org.example.coursework.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.coursework.CommContext;
import org.example.coursework.Part;
import org.example.coursework.SceneAlerts;
import org.example.coursework.Validator;

import java.io.File;
import java.io.IOException;

public class AddController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField brandField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField categoryField;
    @FXML private TextField dateField;
    @FXML private TextField thresholdField;
    @FXML private Label imageFileLabel;

    private File selectedImageFile;

    @FXML
    private void onChooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Part Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.svg"));
        File file = chooser.showOpenDialog(imageFileLabel.getScene().getWindow());
        if (file != null) {
            selectedImageFile = file;
            imageFileLabel.setText(file.getName());
        }
    }

    @FXML
    private void onAddPart() {
        try {
            String id = Validator.validateId(idField.getText(), CommContext.manageInventory.getAllIds());
            String name = Validator.validateName(nameField.getText());
            String brand = Validator.validateBrand(brandField.getText());
            double price = Validator.validatePrice(priceField.getText());
            int quantity = Validator.validateQuantity(quantityField.getText());
            String category = Validator.validateCategory(categoryField.getText());
            String date = Validator.validateDate(dateField.getText());
            int threshold = Validator.validateThreshold(thresholdField.getText());

            String storedImageName = "NULL";
            if (selectedImageFile != null) {
                storedImageName = Part.imageStore(selectedImageFile);
            }

            Part part = new Part(id, name, brand, price, quantity, category, date, storedImageName, threshold);
            CommContext.manageInventory.addPart(part);

            SceneAlerts.showInfo("Part " + id + " (" + name + ") added successfully.");
            clearForm();

        } catch (IOException e) {
            SceneAlerts.showError("Could not save the selected image: " + e.getMessage());
        }
    }

    private void clearForm() {
        idField.clear();
        nameField.clear();
        brandField.clear();
        priceField.clear();
        quantityField.clear();
        categoryField.clear();
        dateField.clear();
        thresholdField.clear();
        imageFileLabel.setText("No image selected");
        selectedImageFile = null;
    }

    @FXML
    private void onBack(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
