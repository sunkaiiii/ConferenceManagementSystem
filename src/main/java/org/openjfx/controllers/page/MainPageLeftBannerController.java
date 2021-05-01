package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageLeftBannerController implements Initializable {
    @FXML
    private HBox conferenceManagement;

    @FXML
    private HBox paperSubmission;

    @FXML
    private HBox reviewManagement;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void onItemClick(MouseEvent event) throws IOException {
        String id = ((Control) event.getSource()).getId();
        switch (id) {
            case "conferenceManagement":
                break;
            case "paperSubmission":
                break;
            case "reviewManagement":
                break;
            default:
                break;
        }
    }
}
