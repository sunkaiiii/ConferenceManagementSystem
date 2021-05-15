package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.controllers.dialog.absdialog.ButtonStyle;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FinalDecisionAlertView extends AbstractAlertDialog {

    @FXML
    private Button negativeButton;

    @FXML
    private Button positiveButton;

    @FXML
    private Label alertContent;

    private Parent root;

    private ButtonStyle positiveButtonStyle;

    private ButtonStyle negativeButtonStyle;

    private WeakReference<Node> callerReference;

    public FinalDecisionAlertView() {
        try {
            FXMLLoader fxmlLoader = SceneHelper.createViewWithResourceName(getClass(), PageNames.FINAL_DECISION_ALERT_VIEW);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            this.root = fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void negativeButtonClicked(MouseEvent event) {
        actionButtonClicked(event, getAlertDialogClickListener()::onNegativeButtonClick);
    }

    @FXML
    void positiveButtonClicked(MouseEvent event) {
        actionButtonClicked(event, getAlertDialogClickListener()::onPositiveButtonClick);
    }

    private void actionButtonClicked(MouseEvent event, Consumer<MouseEvent> mouseEventConsumer) {
        if (getAlertDialogClickListener() != null) {
            mouseEventConsumer.accept(event);
        }
        dismiss();
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

    @Override
    public void show(Node caller) {
        caller.setEffect(new GaussianBlur());
        caller.setDisable(true);
        this.callerReference = new WeakReference<>(caller);
        this.root.setVisible(true);
    }

    @Override
    public void dismiss() {
        Node caller = callerReference.get();
        if (caller != null) {
            caller.setEffect(null);
            caller.setDisable(false);
        }
        this.root.setVisible(false);
    }

    private void setButtonStyle(Button button, ButtonStyle buttonStyle) {
        button.setText(buttonStyle.getButtonText());
        button.setTextFill(buttonStyle.getButtonTextFill());
        button.setStyle(String.format("-fx-background-radius: 8;-fx-background-color: %s", buttonStyle.getButtonBackground()));
    }
}
