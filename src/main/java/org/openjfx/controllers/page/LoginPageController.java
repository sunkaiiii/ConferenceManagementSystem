package org.openjfx.controllers.page;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.AutoTrimTextField;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Admin;
import org.openjfx.model.RegisterdUser;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Registration and login page, where users can login or jump to the registration page to register a new account
 */
public class LoginPageController implements Initializable {
    
    @FXML
    private AutoTrimTextField userName;

    @FXML
    private TextField password;

    List<TextField> textFieldList;

    private final UserService userService = UserService.getDefaultInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldList = new ArrayList<>();
        textFieldList.add(userName);
        textFieldList.add(password);
    }

    @FXML
    public void login(Event event) throws IOException {
        List<TextField> emptyField = InputValidation.findTextFieldIsEmpty(textFieldList);
        Collections.reverse(emptyField);
        if (emptyField.size()>0) {
            DialogHelper.showErrorDialog("Please input your username and password");
            emptyField.forEach(InputValidation::setFocusAndSetErrorStyle);
            return;
        }
        if (userName.getTrimText().equalsIgnoreCase("admin")) {
            Admin admin = userService.adminLogin(userName.getTrimText(), password.getText());
            if(admin!=null){
                jumpToAdminPage(event,admin);
            }

            return;
        }
        RegisterdUser user = userService.checkUserCredential(userName.getTrimText(), password.getText());

        if (user != null) {
            MainApp.getInstance().setUser(user);
            jumpToMainPage(event);
        } else {
            DialogHelper.showErrorDialog("Username or password incorrect");
        }
    }

    private void jumpToAdminPage(Event event, Admin admin) throws IOException {
        FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.ADMIN_PAGE);
        Parent parent = loader.load();
        AdminPageController controller = loader.getController();
        controller.setAdmin(admin);
        SceneHelper.startStage(new Scene(parent),event);
    }

    private void jumpToMainPage(Event event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.SIGN_UP, true);
    }

}