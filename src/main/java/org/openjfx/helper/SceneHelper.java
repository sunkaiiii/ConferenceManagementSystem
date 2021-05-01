package org.openjfx.helper;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.controllers.page.interfaces.PageNameDescriber;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SceneHelper {
    private static final Map<String, Scene> sceneMap = new HashMap<>();

    public static void startPage(Class<?> controllerClazz, Event event, PageNameDescriber resourceFileName, boolean cacheScene) throws IOException {
        Scene scene;
        if (cacheScene) {
            scene = SceneHelper.getSceneFromResourceNameWithCache(controllerClazz, resourceFileName.getPageName());
        } else {
            scene = SceneHelper.getSceneFromResourceName(controllerClazz, resourceFileName.getPageName());
        }
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    private static Scene getSceneFromResourceNameWithCache(Class<?> clazz, String resourceFileName) throws IOException {
        Scene cacheScene = sceneMap.get(resourceFileName);
        if (cacheScene != null) {
            return cacheScene;
        }
        Scene scene = getSceneFromResourceName(clazz, resourceFileName);
        sceneMap.put(resourceFileName, scene);
        return scene;
    }

    private static Scene getSceneFromResourceName(Class<?> clazz, String resourceFileName) throws IOException {
        Parent parent = FXMLLoader.load(clazz.getResource(resourceFileName));
        return new Scene(parent);
    }

    public static void deleteScene(String resourceFileName) {
        sceneMap.remove(resourceFileName);
    }

    public static void addScene(String resourceFileName, Scene scene) {
        sceneMap.put(resourceFileName, scene);
    }
}
