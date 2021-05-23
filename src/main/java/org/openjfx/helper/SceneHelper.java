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

/**
 * Tool class for switching pages
 */
public final class SceneHelper {
    private static final Map<String, Scene> sceneMap = new HashMap<>();

    /**
     * Start a new page
     * @param controllerClazz Classes that are part of this project
     * @param event Event to get the current stage
     * @param resourceFileName Description of the new layout that wants to launch
     * @param cacheScene Whether to cache this page
     * @throws IOException Page file does not exist
     */
    public static void startPage(Class<? extends Initializable> controllerClazz, Event event, PageNameDescriber resourceFileName, boolean cacheScene) throws IOException {
        startPage(controllerClazz,event,resourceFileName,cacheScene,null);
    }

    /**
     * Start a new page with callback
     * @param controllerClazz Classes that are part of this project
     * @param event Event to get the current stage
     * @param resourceFileName Description of the new layout that wants to launch
     * @param cacheScene whether to cache this page
     * @param onStageShowing When the page is loaded, the method executes this callback to provide the caller with the required controller
     * @param <T> Types of Controller
     * @throws IOException Page file does not exist
     */
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

    /**
     * A new page is displayed in the form of a dialog box
     * @param controllerClazz Classes that are part of this project
     * @param stageWidth Width of the dialog
     * @param stageHeight Height of the dialog
     * @param resourceFileName Description of the new layout that wants to launch
     * @param onStageCreated When the page is loaded, the method executes this callback to provide the caller with the required controller
     * @param <T> Types of Controller
     * @throws IOException Page file does not exist
     */
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

    /**
     * Start a new page
     * @param scene Scenes from the new stage
     * @param event Event to get the current stage
     */
    public static void startStage(Scene scene,Event event){
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    /**
     * Start a new page
     * @param controllerClazz Classes that are part of this project
     * @param node node to get the current stage
     * @param resourceFileName Description of the new layout that wants to launch
     * @param cacheScene Whether to cache this page
     * @throws IOException Page file does not exist
     */
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

    /**
     * allow the user to log out
     * @param controllerClazz Classes that are part of this project
     * @param node node to get the current stage
     * @param resourceFileName Description of the new layout that wants to launch
     * @throws IOException Page file does not exist
     */
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

    /**
     * Create a new page with the given resource file name
     * @param clazz Classes that are part of this project
     * @param resourceFileName Description of the new layout that wants to launch
     * @return a loader of a new page
     * @throws IOException Page file does not exist
     */
    public static FXMLLoader createViewWithResourceName(Class<? extends Initializable> clazz,PageNameDescriber resourceFileName)throws IOException{
        return new FXMLLoader(clazz.getResource(resourceFileName.getPageName()));
    }

    /**
     * get the controller of the current stage from scene
     * @param scene a scene that saves the controller from the loader
     * @param <T> the class of controller
     * @return the controller object
     */
    public static <T> T getController(Scene scene){
        return ((FXMLLoader)scene.getUserData()).getController();
    }

    /**
     * delete a specific scene with the layout name
     * @param resourceFileName page name
     */
    public static void deleteScene(String resourceFileName) {
        sceneMap.remove(resourceFileName);
    }

    /**
     * add a scene with the name of the page
     * @param resourceFileName the page name
     * @param scene the scene that wants to be cached
     */
    public static void addScene(String resourceFileName, Scene scene) {
        sceneMap.put(resourceFileName, scene);
    }
}
