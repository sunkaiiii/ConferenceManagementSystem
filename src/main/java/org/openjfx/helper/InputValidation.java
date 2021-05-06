package org.openjfx.helper;

import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

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

    static public boolean isEmailFormat(String text) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(text);
    }


}
