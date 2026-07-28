module org.example.coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens org.example.coursework to javafx.fxml;
    exports org.example.coursework;
    exports org.example.coursework.Controllers;
    opens org.example.coursework.Controllers to javafx.fxml;
}