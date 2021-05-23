package org.openjfx.helper;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.controllers.page.interfaces.PageNameDescriber;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SceneHelper {
    private static final Map<String, Scene> sceneMap = new HashMap<>();

    public static void startPage(Class<? extends Initializable> controllerClazz, Event event, PageNameDescriber resourceFileName, boolean cacheScene) throws IOException {
        startPage(controllerClazz,event,resourceFileName,cacheScene,null);
    }
    public static <T> void startPage(Class<? extends Initializable> controllerClazz, Event event, PageNameDescriber resourceFileName, boolean cacheScene, Consumer<T> onStageShowing) throws IOException {
        Scene scene;
        if (cacheScene) {
            scene = SceneHelper.getSceneFromResourceNameWithCache(controllerClazz, resourceFileName.getPageName());
        } else {
            scene = SceneHelper.getSceneFromResourceName(controllerClazz, resourceFileName.getPageName());
        }
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
        T t = getController(scene);
        if(onStageShowing!=null){
            onStageShowing.accept(t);
        }
    }

    public static <T> void showDialogStage(Class<? extends Initializable> controllerClazz, int stageWidth, int stageHeight, PageNameDescriber resourceFileName, Consumer<T> onStageCreated) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(controllerClazz.getResource(resourceFileName.getPageName()));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,stageWidth,stageHeight);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        T t = fxmlLoader.getController();
        onStageCreated.accept(t);
        stage.showAndWait();
    }

    public static void startStage(Scene scene,Event event){
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public static void startPage(Class<? extends Initializable> controllerClazz, Node node, PageNameDescriber resourceFileName, boolean cacheScene) throws IOException {
        Scene scene;
        if (cacheScene) {
            scene = SceneHelper.getSceneFromResourceNameWithCache(controllerClazz, resourceFileName.getPageName());
        } else {
            scene = SceneHelper.getSceneFromResourceName(controllerClazz, resourceFileName.getPageName());
        }
        Stage appStage = (Stage) (node).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public static void logOut(Class<? extends Initializable> controllerClazz, Node node, PageNameDescriber resourceFileName) throws IOException{
        sceneMap.clear();
        startPage(controllerClazz,node,resourceFileName,false);
    }


    private static Scene getSceneFromResourceNameWithCache(Class<? extends Initializable> clazz, String resourceFileName) throws IOException {
        Scene cacheScene = sceneMap.get(resourceFileName);
        if (cacheScene != null) {
            return cacheScene;
        }
        Scene scene = getSceneFromResourceName(clazz, resourceFileName);
        sceneMap.put(resourceFileName, scene);
        return scene;
    }

    private static Scene getSceneFromResourceName(Class<? extends Initializable> clazz, String resourceFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource(resourceFileName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        scene.setUserData(loader);
        return scene;
    }

    public static FXMLLoader createViewWithResourceName(Class<? extends Initializable> clazz,PageNameDescriber resourceFileName)throws IOException{
        return new FXMLLoader(clazz.getResource(resourceFileName.getPageName()));
    }

    public static <T> T getController(Scene scene){
        return ((FXMLLoader)scene.getUserData()).getController();
    }

    public static void deleteScene(String resourceFileName) {
        sceneMap.remove(resourceFileName);
    }

    public static void addScene(String resourceFileName, Scene scene) {
        sceneMap.put(resourceFileName, scene);
    }
}
