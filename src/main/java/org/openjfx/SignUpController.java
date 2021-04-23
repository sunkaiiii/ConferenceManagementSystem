package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.openjfx.validation.InputValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmedPassword;

    @FXML
    private TextField higestQualification;

    @FXML
    private TextField mobileNumber;

    @FXML
    private TextField interestArea;

    @FXML
    private TextField employerDetails;

    @FXML
    private Label validatioMessage;

    private List<TextField> allTextFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields = new ArrayList<>();
        allTextFields.add(firstName);
        allTextFields.add(lastName);
        allTextFields.add(password);
        allTextFields.add(confirmedPassword);
        allTextFields.add(email);
        allTextFields.add(higestQualification);
        allTextFields.add(mobileNumber);
        allTextFields.add(interestArea);
        allTextFields.add(employerDetails);
    }

    @FXML
    public void back(MouseEvent event) throws IOException{
        Parent signup = FXMLLoader.load(getClass().getResource("log_in.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML public void sign_up(ActionEvent event) throws IOException{
        validatioMessage.setText("");
        Optional<Boolean> validationResult = allTextFields.stream().map(textField -> InputValidation.isNullOrEmpty(textField.getText())).filter(result-> result).findAny();
        if (validationResult.isPresent()){
            validatioMessage.setText("error");
            return;
        }
    }
}
