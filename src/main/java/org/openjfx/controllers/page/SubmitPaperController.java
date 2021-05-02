package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.model.datamodel.Conference;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SubmitPaperController implements Initializable {

    @FXML
    private TextField paperName;

    @FXML
    private Label conferenceName;

    @FXML
    private Label chairName;

    private Conference conference;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
        initPage(conference);
    }

    private void initPage(Conference conference){
        this.chairName.setText(conference.getChairName());
        this.conferenceName.setText(conference.getName());
    }

    @FXML
    void selectPapers(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Paper format",".doc",".docx",".pdf");
        fileChooser.setSelectedExtensionFilter(filter);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(appStage);

    }
}
