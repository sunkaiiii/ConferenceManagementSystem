package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Conference;
import org.openjfx.service.ConferenceService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaperManagementAvailableConferenceController implements Initializable {
    @FXML
    private FlowPane conferenceFlowPane;

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFlowPane();
    }

    private void initFlowPane(){
        try {
            List<Node> availableConference = conferenceService.searchAvailableConference().stream().map(this::createCellWithConference).collect(Collectors.toList());
            this.conferenceFlowPane.getChildren().addAll(availableConference);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private Node createCellWithConference(Conference conference) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.AVAILABLE_CONFERENCE_CELL);
            Parent parent = loader.load();
            PaperPageAvailableConferenceCellController controller = loader.getController();
            controller.setConference(conference);
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
