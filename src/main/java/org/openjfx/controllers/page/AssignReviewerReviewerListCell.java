package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import org.openjfx.model.interfaces.Reviewer;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignReviewerReviewerListCell implements Initializable {

    @FXML
    private Label reviewerName;

    @FXML
    private Label interestArea;

    @FXML
    private Label expertise;

    private Reviewer reviewer;

    private OnReviewerClickListener onCellClickedListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    @FXML
    void reviewerClicked(MouseEvent event) {
        if (onCellClickedListener != null) {
            onCellClickedListener.onClick(event, this.reviewer);
        }
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
        initViews(reviewer);
    }

    private void initViews(Reviewer reviewer) {
        this.reviewerName.setText(reviewer.getReviewerName());
        this.interestArea.setText("(" + reviewer.getInterestAreas().entrySet().iterator().next().getKey() + ")");
        this.expertise.setText("(" + reviewer.getInterestAreas().entrySet().iterator().next().getValue().toString() + ")");
    }

    public OnReviewerClickListener getOnCellClickedListener() {
        return onCellClickedListener;
    }

    public void setOnCellClickedListener(OnReviewerClickListener onCellClickedListener) {
        this.onCellClickedListener = onCellClickedListener;
    }

    public void setRecommend() {
        this.interestArea.setText("(" + reviewer.getInterestAreas().entrySet().iterator().next().getKey() + ")" + "(Recommend Reviewer)");
        this.reviewerName.setTextFill(Paint.valueOf("#2700a6"));
        this.interestArea.setTextFill(Paint.valueOf("#2700a6"));
    }

    public interface OnReviewerClickListener {
        void onClick(MouseEvent event, Reviewer reviewer);
    }
}
