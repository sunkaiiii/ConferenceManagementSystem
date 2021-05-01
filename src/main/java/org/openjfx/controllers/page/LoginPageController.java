package org.openjfx.controllers.page;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.MainApp;
import org.openjfx.controllers.pagemodel.UserController;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.RegisterdUser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    public Label errorMessage;
    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    List<TextField> textFieldList;
    UserController userController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldList = new ArrayList<>();
        textFieldList.add(userName);
        textFieldList.add(password);
    }

    @FXML
    public void login(MouseEvent event) throws IOException {
        if (InputValidation.checkTextFiledIsEmpty(textFieldList)) {
            errorMessage.setText("Please input your username and password");
            return;
        }
        userController = new UserController();
        RegisterdUser user = userController.checkUserCredential(userName.getText(), password.getText());

        if (user != null) {
            MainApp.getInstance().setUser(user);
            jumpToMainPage(event);
        } else {
            errorMessage.setText("Login failed");
            return;
        }
    }

    private void jumpToMainPage(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.SIGN_UP, true);
    }

}