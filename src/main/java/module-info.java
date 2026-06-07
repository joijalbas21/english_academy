module com.englishacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.englishacademy to javafx.fxml;
    opens com.englishacademy.controllers to javafx.fxml;
    opens com.englishacademy.controllers.alumnos to javafx.fxml;
    opens com.englishacademy.controllers.profesores to javafx.fxml;
    opens com.englishacademy.models to javafx.base;
    opens com.englishacademy.views to javafx.fxml;
    opens com.englishacademy.views.alumnos to javafx.fxml;
    opens com.englishacademy.views.profesores to javafx.fxml;
    exports com.englishacademy;
}
