package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.openjfx.model.Review;

import java.net.URL;
import java.util.ResourceBundle;

public class FinalDecisionReviewListCell implements Initializable {

    @FXML
    private Label reviewerName;

    @FXML
    private Label reviewContent;

    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
        initViews(review);
    }

    private void initViews(Review review) {
        this.reviewerName.setText(review.getReviewerDisplayName());
        this.reviewContent.setText(review.getReviewContent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
