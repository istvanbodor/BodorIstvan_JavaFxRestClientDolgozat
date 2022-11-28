package com.example.bodoristvan_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateBooksView extends Controller {

    @FXML
    private TextField titleField;
    @FXML
    private TextField pagesField;
    @FXML
    private TextField availableField;
    @FXML
    private Button submitButton;



    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String title = titleField.getText().trim();
        String pages = pagesField.getText().trim();
        String available = availableField.getText().trim();
        if (title.isEmpty()) {
            warning("Title is required");
            return;
        }
        if (pages.isEmpty()) {
            warning("Pages is required");
            return;
        }
        if (available.isEmpty()) {
            warning("Availability is required");
            return;
        }



        Books newBook = new Books(0, Integer.valueOf(pages), title, Boolean.getBoolean(available) );
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newBook);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                warning("Person added!");
                titleField.setText("");
                pagesField.setText("");
                availableField.setText("");
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
