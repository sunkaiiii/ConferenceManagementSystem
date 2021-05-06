package org.openjfx.controllers.page;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.openjfx.MainApp;
import org.openjfx.controllers.pagemodel.UserController;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Admin;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.service.UserService;

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

    private final UserService userService = UserService.getInstance();

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
        if (userName.getText().equalsIgnoreCase("admin")) {
            Admin admin = userService.adminLogin(userName.getText(), password.getText());
            if(admin!=null){
                jumpToAdminPage(event,admin);
            }

            return;
        }
        RegisterdUser user = userController.checkUserCredential(userName.getText(), password.getText());

        if (user != null) {
            MainApp.getInstance().setUser(user);
            jumpToMainPage(event);
        } else {
            errorMessage.setText("Login failed");
        }
    }

    private void jumpToAdminPage(MouseEvent event, Admin admin) throws IOException {
        FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(),PageNames.ADMIN_PAGE.getPageName());
        Parent parent = loader.load();
        AdminPageController controller = loader.getController();
        controller.setAdmin(admin);
        SceneHelper.startStage(new Scene(parent),event);
    }

    private void jumpToMainPage(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.SIGN_UP, true);
    }

}