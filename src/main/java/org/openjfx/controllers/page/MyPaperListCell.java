package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import org.openjfx.model.datamodel.AuthorInformation;
import org.openjfx.model.datamodel.Paper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyPaperListCell implements Initializable {
    @FXML
    private Label paperName;

    @FXML
    private Label keywords;

    @FXML
    private Label authors;

    @FXML
    private Label submittedName;

    @FXML
    private Label submittedTime;

    @FXML
    private Label paperStatus;

    private Paper paper;


    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
        updateItem(paper);
    }
    protected void updateItem(Paper item) {
        paperName.setText(item.getTitle());
        keywords.setText(String.join(";", item.getKeywords()));
        authors.setText(item.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        submittedName.setText(item.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).findFirst().orElse(""));
        submittedTime.setText(item.getSubmittedTime());
        paperStatus.setText(item.getPaperStatus().getStatus());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
