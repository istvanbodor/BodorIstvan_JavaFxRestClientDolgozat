package com.example.bodoristvan_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class booksviewController extends Controller {


    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Books> booksTable;
    @FXML
    private TableColumn<Books, String> titleCol;
    @FXML
    private TableColumn<Books, Integer> pagesCol;
    @FXML
    private TableColumn<Books, Boolean> availableCol;

    @FXML
    private void initialize() {

        titleCol.setCellValueFactory(new PropertyValueFactory<>("pages"));
        pagesCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
        Platform.runLater(() -> {
            try {
                loadBooksFromServer();
            } catch (IOException e) {
                error("Couldn't get data from server", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void loadBooksFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Books[] books = converter.fromJson(content, Books[].class);
        booksTable.getItems().clear();
        for (Books book : books) {
            booksTable.getItems().add(book);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-people-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create People");
            stage.setScene(scene);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnCloseRequest(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadBooksFromServer();
                } catch (IOException e) {
                    error("An error occurred while communicating with the server");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }


        @FXML
        public void updateClick(ActionEvent actionEvent) {
            int selectedIndex = booksTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                warning("Please select a book from the list first");
                return;
            }
            Books selected = booksTable.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-books-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 480);
                Stage stage = new Stage();
                stage.setTitle("Update People");
                stage.setScene(scene);
                UpdateBooksController controller = fxmlLoader.getController();
                controller.setPerson(selected);
                stage.show();
                insertButton.setDisable(true);
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
                stage.setOnHidden(event -> {
                    insertButton.setDisable(false);
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                    try {
                        loadBooksFromServer();
                    } catch (IOException e) {
                        error("An error occurred while communicating with the server");
                    }
                });
            } catch (IOException e) {
                error("Could not load form", e.getMessage());
            }
        }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectedIndex = booksTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a person from the list first");
            return;
        }

        Books selected = booksTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are you sure you want to delete %s?", selected.getTitle()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()) {
            System.err.println("Unknown error occurred");
            return;
        }
        ButtonType clickedButton = optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)) {
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadBooksFromServer();
            } catch (IOException e) {
                error("An error occurred while communicating with the server");
            }
        }


    }


}