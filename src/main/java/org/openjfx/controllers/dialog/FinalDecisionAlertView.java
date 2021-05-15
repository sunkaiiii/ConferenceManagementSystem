package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.controllers.dialog.absdialog.ButtonStyle;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;

public class FinalDecisionAlertView extends AbstractAlertDialog {

    @FXML
    private Button negativeButton;

    @FXML
    private Button positiveButton;

    @FXML
    private Label alertContent;

    private ButtonStyle positiveButtonStyle;

    private ButtonStyle negativeButtonStyle;

    public FinalDecisionAlertView() {
        try {
            FXMLLoader fxmlLoader = SceneHelper.createViewWithResourceName(getClass(), PageNames.FINAL_DECISION_ALERT_VIEW);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void negativeButtonClicked(MouseEvent event) {
        if (getAlertDialogClickListener() != null) {
            getAlertDialogClickListener().onNegativeButtonClick(event);
        }
    }

    @FXML
    void positiveButtonClicked(MouseEvent event) {
        if (getAlertDialogClickListener() != null) {
            getAlertDialogClickListener().onPositiveButtonClick(event);
        }
    }

    public ButtonStyle getPositiveButtonStyle() {
        return positiveButtonStyle;
    }

    public ButtonStyle getNegativeButtonStyle() {
        return negativeButtonStyle;
    }

    @Override
    public void setPositiveButtonStyle(ButtonStyle style) {
        this.positiveButtonStyle = style;
        setButtonStyle(this.positiveButton, style);
    }

    @Override
    public void setNegativeButtonStyle(ButtonStyle style) {
        this.negativeButtonStyle = style;
        setButtonStyle(this.negativeButton, style);
    }

    @Override
    public void setAlertContent(String content) {
        this.alertContent.setText(content);
    }

    private void setButtonStyle(Button button, ButtonStyle buttonStyle) {
        button.setText(buttonStyle.getButtonText());
        button.setTextFill(buttonStyle.getButtonTextFill());
        button.setStyle(String.format("-fx-background-radius: 8;-fx-background-color: %s", buttonStyle.getButtonBackground()));
    }
}
