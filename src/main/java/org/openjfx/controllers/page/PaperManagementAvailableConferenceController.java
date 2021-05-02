package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.openjfx.service.ConferenceService;

import java.net.URL;
import java.util.ResourceBundle;

public class PaperManagementAvailableConferenceController implements Initializable {
    @FXML
    private FlowPane conferenceFlowPane;

    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
