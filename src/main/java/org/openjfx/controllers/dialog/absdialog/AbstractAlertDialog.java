package org.openjfx.controllers.dialog.absdialog;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractAlertDialog extends StackPane implements Initializable {

    private AlertDialogClickListener alertDialogClickListener;

    private ButtonStyle positiveButtonStyle;

    private ButtonStyle negativeButtonStyle;

    public AlertDialogClickListener getAlertDialogClickListener() {
        return alertDialogClickListener;
    }

    private WeakReference<Node> callerReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAlertDialogClickListener(AlertDialogClickListener alertDialogClickListener) {
        this.alertDialogClickListener = alertDialogClickListener;
    }

    public ButtonStyle getPositiveButtonStyle() {
        return positiveButtonStyle;
    }

    public ButtonStyle getNegativeButtonStyle() {
        return negativeButtonStyle;
    }

    public void setPositiveButtonStyle(ButtonStyle style) {
        this.positiveButtonStyle = style;
        setButtonStyle(getPositiveButton(), style);
    }

    public void setNegativeButtonStyle(ButtonStyle style) {
        this.negativeButtonStyle = style;
        setButtonStyle(getNegativeButton(), style);
    }

    private void setButtonStyle(Button button, ButtonStyle buttonStyle) {
        button.setText(buttonStyle.getButtonText());
        button.setTextFill(buttonStyle.getButtonTextFill());
        button.setStyle(String.format("-fx-background-radius: 8;-fx-background-color: %s", buttonStyle.getButtonBackground()));
    }

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

        Node contentBody = getContentBody();
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
        getRoot().setVisible(true);
    }

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
            Node contentBody = getContentBody();
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
                getRoot().setVisible(false);
            });
        }
    }

    public abstract void setAlertContent(String content);

    public abstract Node getRoot();

    public abstract Node getContentBody();

    public abstract Button getPositiveButton();

    public abstract Button getNegativeButton();

    public interface AlertDialogClickListener {
        default void onNegativeButtonClick(MouseEvent event){}

        void onPositiveButtonClick(MouseEvent event);
    }

}
