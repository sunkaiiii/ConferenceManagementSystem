package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Conference;

import java.io.IOException;
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

    @FXML
    void viewPapers(MouseEvent event) throws IOException {
        FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(),PageNames.CONFERENCE_PAPER.getPageName());
        Parent parent = loader.load();
        ConferencePaperController controller = loader.getController();
        controller.setConference(this.conference);
        SceneHelper.startStage(new Scene(parent),event);
    }

}
