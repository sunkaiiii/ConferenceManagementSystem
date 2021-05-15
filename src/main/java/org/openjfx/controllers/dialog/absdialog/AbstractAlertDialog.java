package org.openjfx.controllers.dialog.absdialog;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractAlertDialog extends StackPane implements Initializable {

    private AlertDialogClickListener alertDialogClickListener;

    public AlertDialogClickListener getAlertDialogClickListener() {
        return alertDialogClickListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAlertDialogClickListener(AlertDialogClickListener alertDialogClickListener) {
        this.alertDialogClickListener = alertDialogClickListener;
    }

    public abstract void setPositiveButtonStyle(ButtonStyle style);

    public abstract void setNegativeButtonStyle(ButtonStyle style);

    public abstract void setAlertContent(String content);

    public interface AlertDialogClickListener {
        default void onNegativeButtonClick(MouseEvent event){}

        void onPositiveButtonClick(MouseEvent event);
    }

}
