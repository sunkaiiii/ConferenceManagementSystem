package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.*;
import org.openjfx.service.PaperService;
import org.openjfx.service.ReviewService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    private final ReviewService reviewService = ReviewService.getDefaultInstance();

    private final PaperService paperService = PaperService.getDefaultInstance();


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
    void rejectClicked(MouseEvent event) {
        DialogHelper.showConfirmDialog("Reject Paper", "Are you sure you want to reject this paper?", "Cancel", "Reject", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                if (writePaperStatus(Paper.PaperStatus.REJECTED)) {
                    refreshPreviousPageAndGoBack(event);
                }
            }
        });
    }

    @FXML
    void acceptClicked(MouseEvent event) {
        DialogHelper.showConfirmDialog("Accept Paper", "Are you sure you want to Accept this paper?", "Cancel", "Accept", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                if (writePaperStatus(Paper.PaperStatus.ACCEPTED)) {
                    refreshPreviousPageAndGoBack(event);
                }
            }
        });
    }

    private boolean writePaperStatus(Paper.PaperStatus status) {
        assert this.paper != null;
        this.paper.setPaperStatus(status);
        try {
            this.paperService.updatePaperStatus(this.paper);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void refreshPreviousPageAndGoBack(MouseEvent event) {
        if (this.finalDecisionAppliedListener != null) {
            this.finalDecisionAppliedListener.finalDecisionApplied();
        }
        backToPreviousPage(event);
    }

    @FXML
    void backToPreviousPage(MouseEvent event) {
        if (this.previousScene != null) {
            SceneHelper.startStage(this.previousScene, event);
        }
    }

    private void initViews(Paper paper) {
        this.paperName.setText(paper.getTitle());
        this.authors.setText(paper.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        List<Node> fileListCell = paper.getPaperFiles()
                .stream()
                .map(this::createFileListCell)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Node> reviewContentCell = paper.getReviewerInformationList()
                .stream()
                .map(ReviewerInformation::getReviewId)
                .map(this::searchReviewById)
                .filter(Objects::nonNull)
                .map(this::createReviewContentCell)
                .collect(Collectors.toList());

        this.reviewContainer.getChildren().setAll(reviewContentCell);
        this.fileContainer.getChildren().setAll(fileListCell);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private Node createFileListCell(PaperFile file) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.WRITE_REVIEW_PAGE_FILE_LIST_CELL);
            Node result = loader.load();
            WriteReviewPageFileListCell cell = loader.getController();
            cell.setPaperFile(file);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Review searchReviewById(String id) {
        Review review = null;
        try {
            review = reviewService.searchReviewById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return review;
    }

    private Node createReviewContentCell(Review review) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.FINAL_DECISION_REVIEW_LIST_CELL);
            Node result = loader.load();
            FinalDecisionReviewListCell cell = loader.getController();
            cell.setReview(review);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public interface FinalDecisionAppliedListener {
        void finalDecisionApplied();
    }
}
