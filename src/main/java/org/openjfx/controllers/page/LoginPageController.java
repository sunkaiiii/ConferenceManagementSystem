package org.openjfx.controllers.page;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class LoginPageController implements Initializable {

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
        if(InputValidation.checkTextFiledIsEmpty(textFieldList)){
            //TODO error message
            return;
        }
        userController = new UserController();
        RegisterdUser user = userController.checkUserCredential(userName.getText(),password.getText());

        if(user != null){
            jumptoMainpage(event);
        }else{
            //TODO error message
            return;
        }
    }

    private void jumptoMainpage(MouseEvent event) throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("main_page_left_banner.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("sign_up.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }

}