package org.openjfx.controllers.page.abspage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.CreateConferenceSelectTimeDialog;
import org.openjfx.controllers.dialog.GeneralAlertView;
import org.openjfx.helper.AutoTrimTextField;
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
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public abstract class AbsConferenceViewController implements Initializable {
    @FXML
    protected Parent rootView;

    @FXML
    protected AutoTrimTextField conferenceName;

    @FXML
    protected AutoTrimTextField conferenceTitle;

    @FXML
    protected AutoTrimTextField conferenceTopic;

    @FXML
    protected GeneralAlertView confirmCreateConferenceView;

    @FXML
    protected AutoTrimTextField keywords;

    @FXML
    protected DateTimePicker deadline;

    private Conference existConference;

    protected List<AutoTrimTextField> AutoTrimTextFields;

    protected final ConferenceService conferenceService = ConferenceService.getDefaultInstance();


    protected abstract void setViewState();

    protected abstract void handleConference(Conference conference) throws IOException;

    private ChangeListener<LocalDate> deadlinePickerValueChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
            showSelectTimeDialog(this, newValue);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AutoTrimTextFields = List.of(conferenceName, conferenceTitle, conferenceTopic, keywords);
        deadline.setFormat(DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.MEDIUM,FormatStyle.MEDIUM, Chronology.ofLocale(Locale.getDefault()),Locale.getDefault()));
        addDateSelectedListener();
        disablePastDates();
        setViewState();
    }

    private void addDateSelectedListener() {
        deadline.valueProperty().addListener(deadlinePickerValueChangeListener);
    }

    private void disablePastDates() {
        deadline.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || item.compareTo(today) < 0);
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
            Scene scene = new Scene(parent, 350, 100);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cancelButtonClicked(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void positiveButtonClicked(MouseEvent event) throws IOException {
        if (InputValidation.checkTextFiledIsEmpty(AutoTrimTextFields)) {
            DialogHelper.showErrorDialog("You need to fill up all fields");
            return;
        }

        LocalDateTime deadlineTime = deadline.getDateTimeValue();
        if (deadlineTime == null || !(LocalDateTime.now().isBefore(deadlineTime) && Duration.between(LocalDateTime.now(), deadlineTime).toDays() > 1)) {
            DialogHelper.showErrorDialog("Deadline must be at least tomorrow");
            return;
        }

        final Conference newConference;
        if (existConference != null) {
            newConference = existConference;
            existConference.setName(conferenceName.getTrimText());
            existConference.setTitle(conferenceTitle.getTrimText());
            existConference.setTopic(conferenceTopic.getTrimText());
            existConference.setKeywords(Arrays.stream(keywords.getTrimText().split(";")).collect(Collectors.toList()));
            existConference.setDeadline(deadline.getDateTimeValue().toString());
        } else {
            newConference = new Conference(conferenceName.getTrimText(), conferenceTitle.getTrimText(), conferenceTopic.getTrimText(), Arrays.stream(keywords.getTrimText().split(";")).collect(Collectors.toList()), deadline.getDateTimeValue().toString());
        }

        if (conferenceService.searchConferenceByName(conferenceName.getTrimText()) != null) {
            if (existConference == null || !conferenceName.getTrimText().equals(existConference.getName())) {
                DialogHelper.showErrorDialog("There has a existed conference with the same name");
                return;
            }

        }
        confirmCreateConferenceView.setAlertDialogClickListener(event1 -> {
            try {
                handleConference(newConference);
                clearCacheSceneData();
                jumpToConferenceManagement(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).show(rootView);

    }


    private void jumpToConferenceManagement(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    private void clearCacheSceneData() {
        SceneHelper.deleteScene(PageNames.CREATE_CONFERENCE.getPageName());
        SceneHelper.deleteScene(PageNames.CONFERENCE_MANAGEMENT.getPageName());
    }

    public Conference getExistConference() {
        return existConference;
    }

    public void setExistConference(Conference existConference) {
        this.existConference = existConference;
        fillViews(existConference);
    }

    private void fillViews(Conference existConference) {
        this.conferenceName.setText(existConference.getName());
        this.conferenceTitle.setText(existConference.getTitle());
        this.conferenceTopic.setText(existConference.getTopic());
        this.keywords.setText(String.join(";", existConference.getKeywords()));
        this.deadline.valueProperty().removeListener(deadlinePickerValueChangeListener);
        this.deadline.setDateTimeValue(LocalDateTime.parse(existConference.getDeadline()));
        this.deadline.valueProperty().addListener(deadlinePickerValueChangeListener);
    }
}
