package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WriteReviewPageController implements Initializable {

    @FXML
    private Label paperName;

    @FXML
    private Label authorNames;

    @FXML
    private Label conferenceName;

    @FXML
    private Label topic;

    @FXML
    private Label keywords;

    @FXML
    private VBox fileContainer;

    @FXML
    private Label submittedTime;

    @FXML
    private TextField reviewContent;

    private Paper paper;



    @FXML
    void cancelSubmit(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(),event,PageNames.REVIEW_MANAGEMENT,true);
    }

    @FXML
    void submitReview(MouseEvent event){

    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
        initViews(paper);
    }

    private void initViews(Paper paper) {
        this.paperName.setText(paper.getTitle());
        this.authorNames.setText(paper.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        this.conferenceName.setText(paper.getConferenceName());
        this.topic.setText(paper.getTopic());
        this.keywords.setText(String.join(";",paper.getKeywords()));
        this.submittedTime.setText(paper.getSubmittedTime());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
