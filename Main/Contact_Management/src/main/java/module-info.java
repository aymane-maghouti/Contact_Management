module com.example.contact_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires log4j;

    opens com.example.contact_management.BO to javafx.base;
    opens com.example.contact_management to javafx.fxml;
    exports com.example.contact_management;
    exports Controller;
    opens Controller to javafx.fxml;
}