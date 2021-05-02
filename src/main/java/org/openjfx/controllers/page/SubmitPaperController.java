package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.openjfx.model.datamodel.Conference;

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
}
