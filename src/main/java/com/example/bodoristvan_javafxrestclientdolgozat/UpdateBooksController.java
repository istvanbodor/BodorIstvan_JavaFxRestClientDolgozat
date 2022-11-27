package com.example.bodoristvan_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateBooksController extends Controller {
    @FXML
    private TextField titleField;
    @FXML
    private TextField pagesField;
    @FXML
    private TextField availableField;

    @FXML
    private Button updateButton;

    private Books book;


    public void setPerson(Books book) {
        this.book = book;
        titleField.setText(this.book.getTitle());
        pagesField.setText(String.valueOf(this.book.getPages()));
        availableField.setText((String.valueOf(this.book.isAvailable())));
    }


    @FXML
    public void updateClick(ActionEvent actionEvent) {
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
            warning("Available is required");
            return;
        }



        this.book.setTitle(title);
        this.book.setPages(Integer.parseInt(pages));
        this.book.setAvailable(Boolean.parseBoolean(available));
        Gson converter = new Gson();
        String json = converter.toJson(this.book);
        try {
            String url = App.BASE_URL + "/" + this.book.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
