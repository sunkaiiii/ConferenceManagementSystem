package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.openjfx.helper.TimeHelper;
import org.openjfx.model.RegisterdUser;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.SimpleFormatter;

public class AdminUserCell implements Initializable {

    @FXML
    private Label userName;

    @FXML
    private Label creationTime;

    private RegisterdUser user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RegisterdUser getUser() {
        return user;
    }

    public void setUser(RegisterdUser user) {
        this.user = user;
        initView(user);
    }

    private void initView(RegisterdUser user) {
        this.userName.setText(String.format("%s %s(%s)", user.getFirstName(), user.getLastName(), user.getUserName()));
        this.creationTime.setText(TimeHelper.convertToDisplayTime(user.getCreationTime()));
    }
}
