module com.example.bodoristvan_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.bodoristvan_javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    exports com.example.bodoristvan_javafxrestclientdolgozat;
}