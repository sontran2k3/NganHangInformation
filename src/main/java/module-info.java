module com.example.nganhanginformation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Entity to javafx.base;


    opens com.example.nganhanginformation to javafx.fxml;
    exports com.example.nganhanginformation;
}