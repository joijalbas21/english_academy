module com.englishacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.englishacademy to javafx.fxml;
    opens com.englishacademy.controllers to javafx.fxml;
    exports com.englishacademy;
}
