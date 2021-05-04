package org.openjfx.helper;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.Optional;

public class InputValidation {
    static public boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    static public boolean checkTextFiledIsEmpty(TextField field) {
        return isNullOrEmpty(field.getText());
    }

    static public boolean checkTextFiledIsEmpty(Collection<TextField> fields) {
        Optional<Boolean> isEmptyResult = fields
                .stream()
                .map(textField -> InputValidation.isNullOrEmpty(textField.getText()))
                .filter(result -> result)
                .findAny();
        return isEmptyResult.isPresent();
    }


}
