package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;

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

    private StatusButtonListener statusButtonListener;


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

    @FXML
    void onStatusButtonClicked(MouseEvent event){
        if(this.statusButtonListener!=null){
            this.statusButtonListener.onStatusButtonClicked(event,this.paper);
        }
    }

    public StatusButtonListener getStatusButtonListener() {
        return statusButtonListener;
    }

    public void setStatusButtonListener(StatusButtonListener statusButtonListener) {
        this.statusButtonListener = statusButtonListener;
    }

    public interface StatusButtonListener{
        void onStatusButtonClicked(MouseEvent event, Paper paper);
    }
}
