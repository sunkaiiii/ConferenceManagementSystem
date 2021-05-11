package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.model.interfaces.Reviewer;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectedReviewerCell implements Initializable {


    @FXML
    private Label reviewerName;

    private Reviewer reviewer;

    private OnCancelClickedListener onCancelClickedListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void cancelClicked(MouseEvent event) {
        if (this.onCancelClickedListener != null) {
            this.onCancelClickedListener.onClick(event, this.reviewer);
        }
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
        initViews(reviewer);
    }

    private void initViews(Reviewer reviewer) {
        this.reviewerName.setText(reviewer.getReviewerName());
    }

    public OnCancelClickedListener getOnCancelClickedListener() {
        return onCancelClickedListener;
    }

    public void setOnCancelClickedListener(OnCancelClickedListener onCancelClickedListener) {
        this.onCancelClickedListener = onCancelClickedListener;
    }

    public interface OnCancelClickedListener {
        void onClick(MouseEvent event, Reviewer reviewer);
    }
}
