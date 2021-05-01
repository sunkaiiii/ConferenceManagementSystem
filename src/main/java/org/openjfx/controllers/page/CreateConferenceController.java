package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.MainApp;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.service.ConferenceService;
import tornadofx.control.DateTimePicker;

import javax.xml.validation.ValidatorHandler;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateConferenceController implements Initializable {
    private static final String CONFERENCE_MANAGEMENT_PAGE_NAME = "conference_management.fxml";
    @FXML
    private TextField conferenceName;

    @FXML
    private TextField conferenceTitle;

    @FXML
    private TextField conferenceTopic;

    @FXML TextField keywords;

    @FXML
    private DateTimePicker deadline;

    List<TextField> textFields;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = List.of(conferenceName,conferenceTitle,conferenceTopic,keywords);
    }

    @FXML
    void cancelCreation(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void createConference(MouseEvent event) throws IOException{
        if(!checkFieldsValid()){
            return;
        }
        Conference newConference = new Conference(conferenceName.getText(),conferenceTitle.getText(),conferenceTopic.getText(), Arrays.stream(keywords.getText().split(",")).collect(Collectors.toList()), deadline.getDateTimeValue().toString());
        saveConferenceDataToDatabase(newConference);
        clearCacheSceneData();
        jumpToConferenceManagement(event);
    }

    private void jumpToConferenceManagement(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(),event,PageNames.CONFERENCE_MANAGEMENT,true);
    }

    private void clearCacheSceneData() {
        SceneHelper.deleteScene(PageNames.CREATE_CONFERENCE.getPageName());
        SceneHelper.deleteScene(PageNames.CONFERENCE_MANAGEMENT.getPageName());
    }

    private void saveConferenceDataToDatabase(Conference newConference) {
        MainApp.getInstance().getUser().createConference(newConference);
    }

    private boolean checkFieldsValid() {
        //TODO validation
        return true;
//        boolean result = InputValidation.checkTextFiledIsEmpty(textFields);
//        LocalDateTime deadlineTime = deadline.getDateTimeValue();
//        result &= deadlineTime != null && LocalDateTime.now().isBefore(deadlineTime) && Duration.between(deadlineTime,LocalDateTime.now()).toDays()>1;
//        return result;
    }
}
