package org.openjfx.helper;

import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

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
