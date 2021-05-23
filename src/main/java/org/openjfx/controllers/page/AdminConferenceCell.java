package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.openjfx.helper.TimeHelper;
import org.openjfx.model.Conference;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminConferenceCell implements Initializable {

    @FXML
    private Label conferenceName;

    @FXML
    private Label title;

    @FXML
    private Label topic;

    @FXML
    private Label deadline;

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
        initViews(conference);
    }

    private void initViews(Conference conference) {
        this.conferenceName.setText(conference.getName());
        this.deadline.setText(TimeHelper.convertToDisplayTime(conference.getDeadline()));
        this.title.setText(conference.getTitle());
        this.topic.setText(conference.getTopic());
        this.chairName.setText(conference.getChairName());
    }
}
