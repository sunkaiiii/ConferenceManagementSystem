package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.helper.TimeHelper;
import org.openjfx.model.Conference;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PaperPageAvailableConferenceCellController implements Initializable {
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
        this.deadline.setText(TimeHelper.convertToDisplayTime(conference.getDeadline()));
        this.keywords.setText(String.join(",", conference.getKeywords()));
        this.chairName.setText(conference.getChairName());
    }

    @FXML
    void goToSubmitPage(MouseEvent event) throws IOException {
        FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.SUBMIT_PAPER);
        Parent node = loader.load();
        Scene scene = new Scene(node);
        SubmitPaperController controller = loader.getController();
        controller.setConference(conference);
        SceneHelper.startStage(scene, event);
    }
}
