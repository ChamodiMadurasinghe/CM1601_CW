package org.example.coursework.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.coursework.*;

import java.util.List;

public class SearchController {

    @FXML private TextField categoryField;
    @FXML private TextField minPriceField;
    @FXML private TextField maxPriceField;
    @FXML private TextField keywordField;

    @FXML private TableView<Part> resultsTable;
    @FXML private TableColumn<Part, String> idColumn;
    @FXML private TableColumn<Part, String> nameColumn;
    @FXML private TableColumn<Part, String> brandColumn;
    @FXML private TableColumn<Part, Number> priceColumn;
    @FXML private TableColumn<Part, Number> quantityColumn;
    @FXML private TableColumn<Part, String> categoryColumn;

    private final MCSearch mcSearch = new MCSearch();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    @FXML
    private void onSearch() {
        try {
            Double minPrice = parseOptionalDouble(minPriceField.getText());
            Double maxPrice = parseOptionalDouble(maxPriceField.getText());

            List<Part> results = MCSearch.search(CommContext.manageInventory.getAllPartsSorted(),
                    categoryField.getText(), minPrice, maxPrice, keywordField.getText());

            resultsTable.setItems(FXCollections.observableArrayList(results));
        } catch (NumberFormatException e) {
            SceneAlerts.showError("Min/Max price must be valid numbers.");
        }
    }

    private Double parseOptionalDouble(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        return Double.parseDouble(text.trim());
    }

    @FXML
    private void onClear() {
        categoryField.clear();
        minPriceField.clear();
        maxPriceField.clear();
        keywordField.clear();
        resultsTable.getItems().clear();
    }

    @FXML
    private void onBack(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
