package org.openjfx.controllers.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.openjfx.MainApp;
import org.openjfx.controllers.dialog.AddInterestingAreaDialog;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.RegisterdUser;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    private Map<String, Integer> interestAreas;

    private final UserService userService = UserService.getDefaultInstance();

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
        interestAreas = new HashMap<>();
    }

    @FXML
    public void back(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.LOG_IN, true);
    }

    @FXML
    void addInterestingAreaClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/openjfx/controllers/dialog/add_interesting_area_dialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddInterestingAreaDialog dialog = fxmlLoader.getController();
        dialog.setOnAddInterestingAreaListener(this::refreshViewWithNewInterestingArea);
        Scene scene = new Scene(parent,350,150);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void refreshViewWithNewInterestingArea(Pair<String, Integer> newInterestingArea) {
        this.interestAreas.put(newInterestingArea.getKey(), newInterestingArea.getValue());
        this.interestArea.setText(this.interestArea.getText() + String.format("(%s:%s)", newInterestingArea.getKey(), newInterestingArea.getValue()));
    }

    @FXML
    public void sign_up(ActionEvent event) throws IOException {
        validationMessage.setText("");
        if (InputValidation.checkTextFiledIsEmpty(allTextFields)) {
            validationMessage.setText("error");
            return;
        }
        if (!InputValidation.isEmailFormat(email.getText())) {
            validationMessage.setText("The email is not in a correct format");
            return;
        }
        if (!InputValidation.checkPasswordFormat(password.getText())) {
            validationMessage.setText("The password is not in a correct format");
            return;
        }
        if (this.interestAreas.size() <= 0) {
            validationMessage.setText("You need to fill up at least 1 interesting area");
            return;
        }
        RegisterdUser newUser = new RegisterdUser(email.getText(), password.getText(), firstName.getText(), lastName.getText(), highestQualification.getText(), this.interestAreas, employerDetails.getText());
        try {
            if (userService.searchAUser(email.getText()) != null) {
                validationMessage.setText("The email has been already registered in the system");
                return;
            }
            userService.addANewUser(newUser);
            MainApp.getInstance().setUser(newUser);
        } catch (IOException e) {
            e.printStackTrace();
            validationMessage.setText("The system could not handle the database");
        }
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }
}
