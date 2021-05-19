package org.openjfx.controllers.page;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.CreateConferenceSelectTimeDialog;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Conference;
import org.openjfx.model.interfaces.Chair;
import org.openjfx.service.ConferenceService;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateConferenceController implements Initializable {
    @FXML
    private TextField conferenceName;

    @FXML
    private TextField conferenceTitle;

    @FXML
    private TextField conferenceTopic;

    @FXML
    TextField keywords;

    @FXML
    private DateTimePicker deadline;

    List<TextField> textFields;

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = List.of(conferenceName, conferenceTitle, conferenceTopic, keywords);
        addDateSelectedListener();
        disablePastDates();
    }

    private void addDateSelectedListener(){
        deadline.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                showSelectTimeDialog(this, newValue);
            }
        });
    }

    private void disablePastDates(){
        deadline.setDayCellFactory(picker->new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || item.compareTo(today) < 0 );
            }
        });
    }

    private void showSelectTimeDialog(ChangeListener<LocalDate> changeListener, LocalDate newValue) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.CREATE_CONFERENCE_ADD_TIME_DIALOG);
            Parent parent = loader.load();
            CreateConferenceSelectTimeDialog dialog = loader.getController();
            dialog.setTimeSelectedListener((hour, minute) -> {
                LocalDateTime selectedTime = newValue.atTime(LocalTime.of(hour, minute));
                deadline.valueProperty().removeListener(changeListener);
                deadline.setDateTimeValue(selectedTime);
                deadline.valueProperty().addListener(changeListener);
            });
            Scene scene = new Scene(parent,350,100);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cancelCreation(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void createConference(MouseEvent event) throws IOException {
        if (InputValidation.checkTextFiledIsEmpty(textFields)) {
            DialogHelper.showErrorDialog("You need to fill up all fields");
            return;
        }

        LocalDateTime deadlineTime = deadline.getDateTimeValue();
        if (deadlineTime == null || !(LocalDateTime.now().isBefore(deadlineTime) && Duration.between(LocalDateTime.now(), deadlineTime).toDays() > 1)) {
            DialogHelper.showErrorDialog("Deadline must be at least tomorrow");
            return;
        }

        Conference newConference = new Conference(conferenceName.getText(), conferenceTitle.getText(), conferenceTopic.getText(), Arrays.stream(keywords.getText().split(",")).collect(Collectors.toList()), deadline.getDateTimeValue().toString());
        if (conferenceService.searchConference(conferenceName.getText()) != null) {
            DialogHelper.showErrorDialog("There has a existed conference with the same name");
            return;
        }
        saveConferenceDataToDatabase(newConference);
        clearCacheSceneData();
        jumpToConferenceManagement(event);
    }

    private void jumpToConferenceManagement(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    private void clearCacheSceneData() {
        SceneHelper.deleteScene(PageNames.CREATE_CONFERENCE.getPageName());
        SceneHelper.deleteScene(PageNames.CONFERENCE_MANAGEMENT.getPageName());
    }

    private void saveConferenceDataToDatabase(Conference newConference) throws IOException {
        Chair chair = MainApp.getInstance().getUser();
        conferenceService.createConference(chair, newConference);
    }

}
