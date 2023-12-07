module com.example.spotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires unirest.java;


    opens com.example.spotify to javafx.fxml, com.google.gson;
    exports com.example.spotify;
}