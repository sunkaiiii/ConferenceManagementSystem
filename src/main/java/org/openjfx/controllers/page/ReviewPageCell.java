package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;
import org.openjfx.service.ConferenceService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReviewPageCell implements Initializable {
    @FXML
    private Label paperName;
    
    @FXML
    private Label submittedTime;
    
    @FXML
    private Label paperAuthor;
    
    @FXML
    private Label paperKeywords;

    @FXML
    private Label conferenceName;

    @FXML
    private Button submitReviewButton;

    @FXML
    private Button seeReviewButton;

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();
    
    private Paper paper;

    public Paper getPaper() {
        return paper;
    }

    private Scene writeReviewScene;

    private CellType cellType;

    public void setPaper(Paper paper) {
        this.paper = paper;
        initViews(paper);
    }

    private void initViews(Paper paper) {
        paperName.setText(paper.getTitle());
        paperAuthor.setText(paper.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        paperKeywords.setText(String.join(";",paper.getKeywords()));
        submittedTime.setText(LocalDateTime.parse(paper.getSubmittedTime()).toLocalDate().toString());
        conferenceName.setText(conferenceService.getConferenceNameById(paper.getConferenceId()));
        switch (this.cellType){
            case REVIEWED:
                this.submitReviewButton.setVisible(false);
                this.seeReviewButton.setVisible(true);
                break;
            case BEING_REVIEW:
                this.submitReviewButton.setVisible(true);
                this.seeReviewButton.setVisible(false);
                break;
        }
    }

    @FXML
    void goToReviewPage(MouseEvent event){
        if(this.writeReviewScene == null){
            try{
                FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.WRITE_REVIEW_PAGE);
                Parent result = loader.load();
                WriteReviewPageController controller = loader.getController();
                controller.setPaper(this.paper);
                this.writeReviewScene = new Scene(result);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        assert this.writeReviewScene!=null;
        SceneHelper.startStage(this.writeReviewScene,event);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public enum CellType{
        BEING_REVIEW,
        REVIEWED
    }
}
