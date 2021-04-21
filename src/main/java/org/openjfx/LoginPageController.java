package org.openjfx;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    ImageView ic;
    @FXML
    Circle pic;
    @FXML
    Circle min;
    @FXML
    Circle close;
    ActionEvent event;

    @FXML
    Label login;
    private double xOffset = 0;
    private double yOffset = 0;

    private void aa(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("jsdfh");
        //  change(this);

    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



//
//        Image image = new Image(getClass().getResource("pic.jpg").toExternalForm());
//        pic.setFill(new ImagePattern(image));

       // login.setText("Login");

    }

    @FXML
    public void login(MouseEvent event)throws IOException{

    }

    @FXML void signup(MouseEvent event)throws  IOException{
        Parent signup = FXMLLoader.load(getClass().getResource("sign_up.fxml"));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signup);
        appStage.setScene(scene);
        appStage.show();
    }


}