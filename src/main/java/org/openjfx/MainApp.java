package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.RegisterdUser;


public class MainApp extends Application {

    private static MainApp Instance;


    private RegisterdUser user;

    public MainApp GetInstance() {
        return Instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("controllers/page/log_in.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        SceneHelper.addScene("log_in.fxml", scene);
        stage.setTitle("FIT5136Assignment");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public RegisterdUser getUser() {
        return user;
    }

    public void setUser(RegisterdUser user) {
        this.user = user;
    }

    public static MainApp getInstance() {
        return Instance;
    }
}