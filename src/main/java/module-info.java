module com.englishacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.englishacademy.views to javafx.fxml;
    exports com.englishacademy;
}
