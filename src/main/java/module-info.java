module com.example.bodoristvan_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bodoristvan_javafxrestclientdolgozat to javafx.fxml;
    exports com.example.bodoristvan_javafxrestclientdolgozat;
}