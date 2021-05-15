package org.openjfx.controllers.dialog;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.controllers.dialog.absdialog.ButtonStyle;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class FinalDecisionAlertView extends AbstractAlertDialog {

    @FXML
    private Button negativeButton;

    @FXML
    private Button positiveButton;

    @FXML
    private Label alertContent;

    @FXML
    private Parent contentBody;

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
        GaussianBlur blur = new GaussianBlur(0);
        caller.setEffect(blur);
        caller.setCache(true);
        caller.setCacheHint(CacheHint.SPEED);
        IntegerProperty value = new SimpleIntegerProperty(0);
        value.addListener((observable, oldV, newV) -> {
            if (newV.intValue() == 24) {
                caller.setCacheHint(CacheHint.QUALITY);
            }
            blur.setRadius(newV.intValue());
        });
        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(value, 24);
        final KeyFrame kf = new KeyFrame(Duration.millis(225), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        contentBody.setCache(true);
        contentBody.setCacheHint(CacheHint.SPEED);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(225), contentBody);
        scaleTransition.setFromX(0.3);
        scaleTransition.setFromY(0.3);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        caller.setDisable(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(225), contentBody);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.play();
        parallelTransition.setOnFinished((event) -> contentBody.setCacheHint(CacheHint.QUALITY));

        this.callerReference = new WeakReference<>(caller);
        this.root.setVisible(true);
    }

    @Override
    public void dismiss() {
        Node caller = callerReference.get();
        if (caller != null) {
            GaussianBlur blur = new GaussianBlur(24);
            caller.setEffect(blur);
            caller.setCache(true);
            caller.setCacheHint(CacheHint.SPEED);
            IntegerProperty value = new SimpleIntegerProperty(0);
            value.addListener((observable, oldV, newV) -> {
                if (newV.intValue() == 24) {
                    caller.setCacheHint(CacheHint.QUALITY);
                    caller.setEffect(null);
                    caller.setDisable(false);
                    return;
                }
                blur.setRadius(24 - newV.intValue());
            });
            Timeline timeline = new Timeline();
            final KeyValue kv = new KeyValue(value, 24);
            final KeyFrame kf = new KeyFrame(Duration.millis(150), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            contentBody.setCache(true);
            contentBody.setCacheHint(CacheHint.SPEED);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), contentBody);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.3);
            scaleTransition.setToY(0.3);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), contentBody);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
            parallelTransition.play();
            parallelTransition.setOnFinished((event) -> {
                contentBody.setCacheHint(CacheHint.QUALITY);
                this.root.setVisible(false);
            });
        }
    }

    private void setButtonStyle(Button button, ButtonStyle buttonStyle) {
        button.setText(buttonStyle.getButtonText());
        button.setTextFill(buttonStyle.getButtonTextFill());
        button.setStyle(String.format("-fx-background-radius: 8;-fx-background-color: %s", buttonStyle.getButtonBackground()));
    }
}
