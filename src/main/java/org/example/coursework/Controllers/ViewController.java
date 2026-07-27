package org.example.coursework.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.coursework.*;

import java.util.Locale;

public class ViewController {

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, String> idColumn;
    @FXML private TableColumn<Part, String> nameColumn;
    @FXML private TableColumn<Part, String> brandColumn;
    @FXML private TableColumn<Part, Number> priceColumn;
    @FXML private TableColumn<Part, Number> quantityColumn;
    @FXML private TableColumn<Part, String> categoryColumn;
    @FXML private TableColumn<Part, String> dateColumn;
    @FXML private TableColumn<Part, Number> thresholdColumn;
    @FXML private TableColumn<Part, String> imageColumn;

    @FXML private Label totalCountLabel;
    @FXML private Label totalValueLabel;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("lowStockThreshold"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageFile"));

        // Show the part's picture inside the table cell instead of just its file name
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageFileName, boolean empty) {
                super.updateItem(imageFileName, empty);
                if (empty || imageFileName == null) {
                    setGraphic(null);
                    return;
                }
                Image image = Part.loadImage(imageFileName);
                if (image == null) {
                    setGraphic(null);
                    setText("No image");
                } else {
                    setText(null);
                    imageView.setImage(image);
                    imageView.setFitWidth(48);
                    imageView.setFitHeight(48);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });

        refresh();
    }

    private void refresh() {
        partsTable.setItems(FXCollections.observableArrayList(CommContext.manageInventory.getAllPartsSorted()));
        totalCountLabel.setText("Total Item Count: " + CommContext.manageInventory.getTotalItemCount());
        totalValueLabel.setText(String.format(Locale.ENGLISH, "Total Inventory Value: Rs. %.2f",
                CommContext.manageInventory.getTotalInventoryValue()));
    }

    @FXML
    private void onBack(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
