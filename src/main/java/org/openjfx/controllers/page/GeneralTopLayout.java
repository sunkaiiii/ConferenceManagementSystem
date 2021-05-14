package org.openjfx.controllers.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import org.openjfx.MainApp;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralTopLayout extends StackPane implements Initializable {
    @FXML
    private Label title;

    private String titleText;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem logOut;

    public GeneralTopLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("general_top_layout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        }catch (IOException exception){
            throw  new RuntimeException(exception);
        }
        this.title.setText(titleText);
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        this.title.setText(titleText);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuButton.setText(MainApp.getInstance().getUser().getUserName());
        initItemSelectedAction();
    }

    private void initItemSelectedAction() {
        logOut.setOnAction(this::logOut);
    }

    private void logOut(ActionEvent event) {
        try {
            SceneHelper.logOut(getClass(), menuButton, PageNames.LOG_IN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
