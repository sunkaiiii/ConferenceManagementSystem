package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateConferenceController implements Initializable {
    private static final String CONFERENCE_MANAGEMENT_PAGE_NAME = "conference_management.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void cancelCreation(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }
}
