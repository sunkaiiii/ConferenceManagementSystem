package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;


/**
 * Dialog used in Create and Edit conference page to allow the user select hour and minute for deadline
 */
public class CreateConferenceSelectTimeDialog {
    @FXML
    private TextField hour;

    @FXML
    private TextField minute;

    private TimeSelectedListener timeSelectedListener;

    @FXML
    private void cancelClicked(MouseEvent event) {
        closeDialog(event);
    }

    private void closeDialog(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void okClicked(MouseEvent event){
        if(InputValidation.checkTextFiledIsEmpty(hour)){
            DialogHelper.showErrorDialog("You need to input an number of hour");
            InputValidation.setFocusAndSetErrorStyle(hour);
            return;
        }
        if(InputValidation.checkTextFiledIsEmpty(minute)){
            DialogHelper.showErrorDialog("You need to input an number of minutes");
            InputValidation.setFocusAndSetErrorStyle(minute);
            return;
        }
        try{
            int hourNumber = Integer.parseInt(hour.getText());
            int minuteNumber = Integer.parseInt(minute.getText());
            if(hourNumber >= 24 || hourNumber<0){
                DialogHelper.showErrorDialog("The hour number cannot over 23 or lower than 0");
                InputValidation.setFocusAndSetErrorStyle(hour);
                return;
            }
            if(minuteNumber<0||minuteNumber>=60){
                DialogHelper.showErrorDialog("The minute number cannot over 60 or lower than 0");
                InputValidation.setFocusAndSetErrorStyle(minute);
                return;
            }
            if(timeSelectedListener!=null){
                timeSelectedListener.timeSelected(hourNumber,minuteNumber);
            }
            closeDialog(event);
        }catch (NumberFormatException e){
            DialogHelper.showErrorDialog("You need to input a number format");
        }
    }

    public TimeSelectedListener getTimeSelectedListener() {
        return timeSelectedListener;
    }

    public void setTimeSelectedListener(TimeSelectedListener timeSelectedListener) {
        this.timeSelectedListener = timeSelectedListener;
    }

    public  interface TimeSelectedListener{
        void timeSelected(int hour, int minute);
    }
}
