package org.openjfx.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Tools for displaying e.g. error pop-ups and generic OK Cancel pop-ups
 */
public final class DialogHelper {
    static public void showErrorDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Error");
        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.showAndWait();
    }

    static public void showConfirmDialog(String title, String message, ConfirmDialogClickListener listener) {
        showConfirmDialog(title, message, "Cancel", "Confirm", listener);
    }

    static public void showConfirmDialog(String title, String message, String negativeText, String positiveText, ConfirmDialogClickListener listener) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(title);
        dialog.setContentText(message);
        ButtonType cancel = new ButtonType(negativeText, ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType ok = new ButtonType(positiveText, ButtonBar.ButtonData.YES);
        dialog.getDialogPane().getButtonTypes().setAll(ok, cancel);
        dialog.showAndWait().ifPresent(type -> {
            if (type == cancel) {
                listener.onNegativeButtonClick();
            } else {
                listener.onPositiveButtonClick();
            }
        });

    }

    public interface ConfirmDialogClickListener {
        void onNegativeButtonClick();

        void onPositiveButtonClick();
    }
}
