package org.openjfx.helper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tool class for input validation
 */
public final class InputValidation {
    static public boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty() || text.trim().isBlank();
    }

    static public <T extends TextField> boolean checkTextFiledIsEmpty(T field) {
        return isNullOrEmpty(field.getText());
    }

    static public <T extends TextField> boolean checkTextFiledIsEmpty(Collection<T> fields) {
        Optional<Boolean> isEmptyResult = fields
                .stream()
                .map(textField -> InputValidation.isNullOrEmpty(textField.getText()))
                .filter(result -> result)
                .findAny();
        return isEmptyResult.isPresent();
    }

    /**
     * When an input box does not meet the validation criteria, you can make it checked and indicate an error in red
     * @param field The textfield to be focused and indicated
     */
    public static void setFocusAndSetErrorStyle(TextField field){
        final String previousStyle = field.getStyle();
        field.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
        field.requestFocus();
        field.selectAll();
        field.textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                field.setStyle(previousStyle);
                field.textProperty().removeListener(this);
            }
        });
    }

    static public List<TextField> findTextFieldIsEmpty(Collection<TextField> fields){
        return fields.stream().filter(InputValidation::checkTextFiledIsEmpty).collect(Collectors.toList());
    }

    static public boolean isEmailFormat(String text) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(text);
    }

    /**
     * Acceptable password formats must contain at least one number, one uppercase letter and one lowercase letter
     * @param password A string of password
     * @return the format of the password is correct
     */
    static public boolean checkPasswordFormat(String password) {
        boolean result = password.length() >= 8;
        result &= hasDigit(password);
        result &= hasLowerCase(password);
        result &= hasUpperCase(password);
        return result;
    }

    static private boolean hasDigit(String text){
        for(char c:text.toCharArray()){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    static private boolean hasUpperCase(String text){
        for(char c:text.toCharArray()){
            if(Character.isUpperCase(c)){
                return true;
            }
        }
        return false;
    }

    static private boolean hasLowerCase(String text){
        for(char c:text.toCharArray()){
            if(Character.isLowerCase(c)){
                return true;
            }
        }
        return false;
    }


}
