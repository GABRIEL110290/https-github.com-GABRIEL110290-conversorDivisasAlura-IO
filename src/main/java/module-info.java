module com.example.conversordivisasalura {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.conversordivisasalura to javafx.fxml;
    exports com.example.conversordivisasalura;
}