module com.example.metrolost_n_found {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.metrolost_n_found to javafx.fxml;
    exports com.example.metrolost_n_found;
}