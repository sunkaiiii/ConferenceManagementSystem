package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;

import java.net.URL;
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
    
    private Paper paper;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
        initViews(paper);
    }

    private void initViews(Paper paper) {
        paperName.setText(paper.getTitle());
        paperAuthor.setText(paper.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        paperKeywords.setText(String.join(";",paper.getKeywords()));
        submittedTime.setText(paper.getSubmittedTime());
    }

    @FXML
    void goToReviewPage(MouseEvent event){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
