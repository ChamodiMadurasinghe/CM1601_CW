package org.example.coursework.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.coursework.CommContext;
import org.example.coursework.LSM;
import org.example.coursework.Part;
import org.example.coursework.SceneAlerts;

import java.util.List;

public class MainMenuController {

    @FXML
    private ListView<String> lowStockListView;

    @FXML
    public void initialize() {
        refreshLowStockList();
    }

    private void refreshLowStockList() {
        lowStockListView.getItems().clear();
        List<Part> lowStockParts = new LSM().findLSParts(CommContext.manageInventory.getAllPartsSorted());
        for (Part p : lowStockParts) {
            lowStockListView.getItems().add(p.getName() + " (" + p.getId() + ") - Qty: " + p.getQuantity()
                    + " (Threshold: " + p.getLowStockThreshold() + ")");
        }
        if (lowStockParts.isEmpty()) {
            lowStockListView.getItems().add("No low stock parts.");
        }
    }

    @FXML
    private void onRefreshLowStock() {
        refreshLowStockList();
    }

    @FXML
    private void onAddPart() {
        SceneAlerts.openWindow("add-item.fxml", "Add New Part");
    }

    @FXML
    private void onUpdatePart() {
        SceneAlerts.openWindow("update-item.fxml", "Update Part");
    }

    @FXML
    private void onDeletePart() {
        SceneAlerts.openWindow("delete-item.fxml", "Delete Part");
    }

    @FXML
    private void onViewInventory() {
        SceneAlerts.openWindow("view-inventory.fxml", "All Inventory Parts");
    }

    @FXML
    private void onSearch() {
        SceneAlerts.openWindow("search.fxml", "Search Parts");
    }

    @FXML
    private void onCart() {
        SceneAlerts.openWindow("cart.fxml", "Point of Sale");
    }

    @FXML
    private void onViewDealers() {
        SceneAlerts.openWindow("dealers.fxml", "Dealers");
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }
}
