package org.example.coursework.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.coursework.CommContext;
import org.example.coursework.Part;
import org.example.coursework.SceneAlerts;

import java.util.Optional;

public class DeleteController {

    @FXML private TextField idField;
    @FXML private Label detailsLabel;

    private Part foundPart;

    @FXML
    private void onFind() {
        foundPart = CommContext.manageInventory.findById(idField.getText());
        if (foundPart == null) {
            detailsLabel.setText("No part found with that ID.");
        } else {
            detailsLabel.setText(foundPart.getName() + " | " + foundPart.getBrand()
                    + " | Qty: " + foundPart.getQuantity() + " | Rs. " + foundPart.getPrice());
        }
    }

    @FXML
    private void onDelete() {
        if (foundPart == null) {
            SceneAlerts.showError("Find a valid part ID first.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete part " + foundPart.getId() + " (" + foundPart.getName() + ")?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                CommContext.manageInventory.deletePart(foundPart.getId());
                SceneAlerts.showInfo("Part deleted successfully.");
                foundPart = null;
                idField.clear();
                detailsLabel.setText("");
            } catch (IllegalArgumentException e) {
                SceneAlerts.showError(e.getMessage());
            }
        }
    }

    @FXML
    private void onClose(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
