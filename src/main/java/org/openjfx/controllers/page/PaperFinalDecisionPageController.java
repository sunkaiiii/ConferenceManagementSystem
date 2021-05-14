package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Paper;

import java.net.URL;
import java.util.ResourceBundle;

public class PaperFinalDecisionPageController implements Initializable {

    @FXML
    private Label paperName;

    @FXML
    private Label authors;

    @FXML
    private VBox fileContainer;

    @FXML
    private VBox reviewContainer;


    private Paper paper;

    private FinalDecisionAppliedListener finalDecisionAppliedListener;

    private Scene previousScene;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
        initViews(paper);
    }


    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public FinalDecisionAppliedListener getFinalDecisionAppliedListener() {
        return finalDecisionAppliedListener;
    }

    public void setFinalDecisionAppliedListener(FinalDecisionAppliedListener finalDecisionAppliedListener) {
        this.finalDecisionAppliedListener = finalDecisionAppliedListener;
    }

    @FXML
    void rejectClicked(MouseEvent event){

    }

    @FXML
    void acceptClicked(MouseEvent event){

    }

    @FXML
    void backToPreviousPage(MouseEvent event){
        if(this.previousScene!=null){
            SceneHelper.startStage(this.previousScene,event);
        }
    }

    private void initViews(Paper paper) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public interface FinalDecisionAppliedListener{
        void finalDecisionApplied();
    }
}
