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
import javafx.scene.paint.Paint;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.helper.TimeHelper;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    private PaperService paperService = PaperService.getDefaultInstance();

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
        if (this.conference == null) {
            return;
        }
        this.conferenceName.setText(conference.getName());
        this.topic.setText(conference.getTopic());
        this.deadline.setText(TimeHelper.convertToDisplayTime(conference.getDeadline()));
        Arrays.stream(Paper.PaperStatus.values())
                .map(status -> String.format("Paper %s: %d", status.getStatus(), findPaperWithStatus(this.conference, status).size()))
                .map(paperInformation -> {
                    Label label = new Label();
                    label.setTextFill(Paint.valueOf("#000000"));
                    label.setText(paperInformation);
                    return label;
                })
                .forEach(this.paperInformation.getChildren()::add);

    }

    private List<Paper> findPaperWithStatus(Conference conference, Paper.PaperStatus status) {
        try {
            return paperService.findPaperWithStatus(conference, status);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @FXML
    void viewPapers(MouseEvent event) throws IOException {
        FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.CONFERENCE_PAPER);
        Parent parent = loader.load();
        ConferencePaperController controller = loader.getController();
        controller.setConference(this.conference);
        SceneHelper.startStage(new Scene(parent), event);
    }

    @FXML
    void editConference(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.EDIT_CONFERENCE, false, (EditConferenceController controller) -> {
            controller.setExistConference(this.conference);
        });

    }

    public void setConferenceFinishedLayout() {
        this.edit.setVisible(false);
    }
}
