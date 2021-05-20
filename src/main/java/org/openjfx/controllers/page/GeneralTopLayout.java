package org.openjfx.controllers.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.RegisterdUser;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GeneralTopLayout extends StackPane implements Initializable {
    @FXML
    private Label title;

    @FXML
    private ImageView userImage;

    private String titleText;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem logOut;

    private boolean isAdminView;

    private static final double HUE_OFFSET = new Random().nextDouble() * (new Random().nextInt() % 2 == 0 ? -1 : 1);


    public GeneralTopLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("general_top_layout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.title.setText(titleText);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(HUE_OFFSET);
        this.userImage.setEffect(colorAdjust);
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
        menuButton.setText(isAdminView ? "Admin" : tryToGetUserName());
        initItemSelectedAction();
    }

    private void initItemSelectedAction() {
        logOut.setOnAction(this::logOut);
    }

    private void logOut(ActionEvent event) {
        DialogHelper.showConfirmDialog("Log out", "Are you sure you want to log out?", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                try {
                    SceneHelper.logOut(GeneralTopLayout.this.getClass(), menuButton, PageNames.LOG_IN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean isAdminView() {
        return isAdminView;
    }

    public void setAdminView(boolean adminView) {
        isAdminView = adminView;
        menuButton.setText(isAdminView ? "Admin" : tryToGetUserName());
    }

    private String tryToGetUserName(){
        RegisterdUser user = MainApp.getInstance().getUser();
        return user==null?"Unknown User":user.getUserName();
    }
}
