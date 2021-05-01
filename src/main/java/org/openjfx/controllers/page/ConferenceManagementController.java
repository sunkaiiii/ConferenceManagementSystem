package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConferenceManagementController implements Initializable {
    private static final String CREATE_CONFERENCE_PAGE_RESOURCE_NAME = "create_conference.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void gotoCreateConferencePage(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CREATE_CONFERENCE, true);
    }
}