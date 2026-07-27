package org.example.coursework.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.coursework.*;

import java.util.List;

public class DealerController {

    @FXML private TableView<Dealer> dealersTable;
    @FXML private TableColumn<Dealer, String> idColumn;
    @FXML private TableColumn<Dealer, String> nameColumn;
    @FXML private TableColumn<Dealer, String> contactColumn;
    @FXML private TableColumn<Dealer, String> locationColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        onPickDealers();
    }

    @FXML
    private void onPickDealers() {
        List<Dealer> dealers = CommContext.dealerSelector.getRandomDealerSortByLocation(4);
        if (dealers.isEmpty()) {
            SceneAlerts.showError("Not enough dealers on file to select 4 unique dealers.");
            return;
        }
        dealersTable.setItems(FXCollections.observableArrayList(dealers));
    }

    @FXML
    private void onBack(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
