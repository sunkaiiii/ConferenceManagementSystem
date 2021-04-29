package org.openjfx.controllers.page;

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
import javafx.stage.Stage;
import org.openjfx.controllers.pagemodel.UserController;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.validation.InputValidation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TextField highestQualification;

    @FXML
    private TextField mobileNumber;

    @FXML
    private TextField interestArea;

    @FXML
    private TextField employerDetails;

    @FXML
    private Label validationMessage;

    private List<TextField> allTextFields;

    private UserController userController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields = new ArrayList<>();
        allTextFields.add(firstName);
        allTextFields.add(lastName);
        allTextFields.add(password);
        allTextFields.add(confirmedPassword);
        allTextFields.add(email);
        allTextFields.add(highestQualification);
        allTextFields.add(mobileNumber);
        allTextFields.add(interestArea);
        allTextFields.add(employerDetails);
    }

    @FXML
    public void back(MouseEvent event) throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("log_in.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    public void sign_up(ActionEvent event) throws IOException {
        validationMessage.setText("");
        if (InputValidation.checkTextFiledIsEmpty(allTextFields)) {
            validationMessage.setText("error");
            return;
        }
        if(userController == null){
            userController = new UserController();
        }
        RegisterdUser newUser = new RegisterdUser(email.getText(),password.getText(),firstName.getText(),lastName.getText(),highestQualification.getText(),interestArea.getText(),employerDetails.getText());
        try{
            userController.createANewUser(newUser);
        } catch (UserController.CreateUserException e) {
            e.printStackTrace();
            validationMessage.setText("The email has been already registered in the system");
        }catch (IOException e){
            e.printStackTrace();
            validationMessage.setText("The system could not handle the database");
        }
        Parent signup = FXMLLoader.load(getClass().getResource("main_page_left_banner.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }
}
