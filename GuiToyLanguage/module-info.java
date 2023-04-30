module com.example.guitoylanguage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guitoylanguage to javafx.fxml;
    exports com.example.guitoylanguage;
}