package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Conference;
import org.openjfx.service.ConferenceService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Show all Conferences belonging to this user
 */
public class ConferenceManagementController implements Initializable {
    @FXML
    private FlowPane onGoingConferenceFlowPane;

    @FXML
    private FlowPane finishedConferenceFLowPane;

    @FXML
    private Label onGoingConferenceLabel;

    @FXML
    private Label finishedConferenceLabel;

    @FXML
    private Label noContentLabel;

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPage();
    }

    @FXML
    void gotoCreateConferencePage(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CREATE_CONFERENCE, true);
    }

    private void initPage() {
        List<Conference> userConferences;
        try {
            userConferences = conferenceService.searchUsersConference(MainApp.getInstance().getUser());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        List<Node> onGoingConference = userConferences.stream().filter(this::isConferenceOnGoing).map(this::createCellWithConference).filter(Objects::nonNull).collect(Collectors.toList());
        List<Node> finishedConference = userConferences.stream().filter(this::isConferenceFinished).map(this::createCellWithConference).filter(Objects::nonNull).collect(Collectors.toList());
        noContentLabel.setVisible(onGoingConference.size() == 0 && finishedConference.size() == 0);
        onGoingConferenceLabel.setVisible(onGoingConference.size() > 0);
        finishedConferenceLabel.setVisible(finishedConference.size() > 0);
        onGoingConferenceFlowPane.setVisible(onGoingConference.size() > 0);
        finishedConferenceFLowPane.setVisible(finishedConference.size() > 0);
        onGoingConferenceFlowPane.getChildren().addAll(onGoingConference);
        finishedConferenceFLowPane.getChildren().addAll(finishedConference);
    }

    private boolean isConferenceFinished(Conference conference) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.parse(conference.getDeadline());
        return now.isAfter(deadline);
    }

    private boolean isConferenceOnGoing(Conference conference) {
        return !isConferenceFinished(conference);
    }

    private Node createCellWithConference(Conference conference) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.CONFERENCE_CELL);
            Parent parent = loader.load();
            ConferenceCellController controller = loader.getController();
            controller.setConference(conference);
            if(isConferenceFinished(conference)){
                controller.setConferenceFinishedLayout();
            }
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
