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
import java.util.stream.Collectors;

public class PaperPageAvailableConferenceController implements Initializable {
    @FXML
    private Label conferenceName;

    @FXML
    private Label topic;

    @FXML
    private Label deadline;

    @FXML
    private Label chairName;

    @FXML
    private Label keywords;

    private Conference conference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
        initCell(conference);
    }

    private void initCell(Conference conference) {
        this.conferenceName.setText(conference.getName());
        this.topic.setText(conference.getTopic());
        this.deadline.setText(LocalDateTime.parse(conference.getDeadline()).toLocalDate().toString());
        this.keywords.setText(String.join(",", conference.getKeywords()));
        this.chairName.setText(conference.getChairName());
    }
}
