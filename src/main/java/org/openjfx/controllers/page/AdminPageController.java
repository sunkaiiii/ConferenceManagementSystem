package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Admin;
import org.openjfx.model.Conference;
import org.openjfx.model.RegisterdUser;
import org.openjfx.service.ConferenceService;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminPageController implements Initializable {
    @FXML
    private FlowPane conferenceListContainer;

    @FXML
    private VBox userListContainer;

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();

    private final UserService userService = UserService.getDefaultInstance();

    private Admin admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initPage(Admin admin) {
        try {
            List<Node> allConference = conferenceService.searchAllConference().stream().map(this::getConferenceCell).filter(Objects::nonNull).collect(Collectors.toList());
            conferenceListContainer.getChildren().setAll(allConference);
            List<Node> allUser = userService.findAllUser(admin).stream().map(this::getUserCell).filter(Objects::nonNull).collect(Collectors.toList());
            userListContainer.getChildren().setAll(allUser);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private Node getConferenceCell(Conference conference) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.ADMIN_CONFERENCE_CELL);
            Node result = loader.load();
            AdminConferenceCell conferenceCell = loader.getController();
            conferenceCell.setConference(conference);
            return result;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Node getUserCell(RegisterdUser user) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.ADMIN_USER_CELL);
            Node result = loader.load();
            AdminUserCell cell = loader.getController();
            cell.setUser(user);
            return result;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        initPage(admin);
    }
}
