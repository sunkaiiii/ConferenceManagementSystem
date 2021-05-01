package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.openjfx.model.datamodel.Conference;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ConferenceCellController implements Initializable {
    @FXML
    private Label conferenceName;

    @FXML
    private Label topic;

    @FXML
    private Label deadline;

    @FXML
    private Button edit;

    @FXML
    private Button viewPapers;

    @FXML
    private VBox paperInformation;

    private Conference conference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCell();
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
        initCell();
    }

    private void initCell() {
        if(this.conference == null){
            return;
        }
        this.conferenceName.setText(conference.getName());
        this.topic.setText(conference.getTopic());
        this.deadline.setText(LocalDateTime.parse(conference.getDeadline()).toLocalDate().toString());
    }

}
