package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.MainApp;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.interfaces.Chair;
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

    private final ConferenceService conferenceService = ConferenceService.getInstance();


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

    private void saveConferenceDataToDatabase(Conference newConference) throws IOException {
        Chair chair = MainApp.getInstance().getUser();
        conferenceService.createConference(chair,newConference);
    }

    private boolean checkFieldsValid() {
        boolean result = !InputValidation.checkTextFiledIsEmpty(textFields);
        LocalDateTime deadlineTime = deadline.getDateTimeValue();
        result &= deadlineTime != null && LocalDateTime.now().isBefore(deadlineTime) && Duration.between(LocalDateTime.now(),deadlineTime).toDays()>1;
        return result;
    }
}
