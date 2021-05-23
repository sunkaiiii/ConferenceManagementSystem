package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.GeneralAlertView;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.helper.*;
import org.openjfx.model.*;
import org.openjfx.service.ConferenceService;
import org.openjfx.service.ReviewService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WriteReviewPageController implements Initializable {

    @FXML
    private BorderPane rootView;

    @FXML
    private GeneralAlertView alertView;

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
    private StackPane submitReviewPane;

    @FXML
    private Label cancelSubmitLabel;

    @FXML
    private Label submittedTime;

    @FXML
    private AutoTrimTextField reviewContent;

    private List<WriteReviewPageFileListCell> fileListCellList;

    private Paper paper;

    private final ReviewService reviewService = ReviewService.getDefaultInstance();

    private final ConferenceService conferenceService = ConferenceService.getDefaultInstance();

    @FXML
    void cancelSubmit(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.REVIEW_MANAGEMENT, true);
    }

    @FXML
    void submitReview(MouseEvent event) {
        if (!validation()) {
            return;
        }
        assert this.paper != null;
        Review review = new Review(this.paper.getId(), this.reviewContent.getTrimText(), MainApp.getInstance().getUser().getReviewerIdentifiedName(), MainApp.getInstance().getUser().getReviewerName());
        alertView.setAlertDialogClickListener(new AbstractAlertDialog.AlertDialogClickListener() {
            @Override
            public void onPositiveButtonClick(MouseEvent event) {
                try {
                    writeReview(event, review);
                    backToPreviousPageAndRefresh(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertView.show(this.rootView);

    }

    private void writeReview(MouseEvent event, Review review) throws IOException {
        reviewService.addReview(review);
    }

    private void backToPreviousPageAndRefresh(MouseEvent event) throws IOException {
        SceneHelper.deleteScene(PageNames.REVIEW_MANAGEMENT.getPageName());
        SceneHelper.startPage(getClass(), event, PageNames.REVIEW_MANAGEMENT, true);
    }


    private boolean validation() {
        boolean isReadAllFiles = fileListCellList.stream().allMatch(WriteReviewPageFileListCell::isRead);
        if (!isReadAllFiles) {
            DialogHelper.showErrorDialog("You need to read all papers to write the review");
            return false;
        }
        if (InputValidation.checkTextFiledIsEmpty(this.reviewContent)) {
            DialogHelper.showErrorDialog("You need to write a review");
            return false;
        }
        return true;
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
        this.conferenceName.setText(conferenceService.getConferenceNameById(paper.getConferenceId()));
        this.topic.setText(paper.getTopic());
        this.keywords.setText(String.join(";", paper.getKeywords()));
        this.submittedTime.setText(paper.getSubmittedTime());
        this.fileContainer.getChildren().setAll(paper.getPaperFiles().stream().map(this::createReviewFileListCell).collect(Collectors.toList()));
        this.fileContainer.setBorder(ViewHelper.createGeneralDashBolder("#a5b9ff"));
    }

    private Node createReviewFileListCell(PaperFile file) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.WRITE_REVIEW_PAGE_FILE_LIST_CELL);
            Node result = loader.load();
            WriteReviewPageFileListCell cell = loader.getController();
            fileListCellList.add(cell);
            cell.setPaperFile(file);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileListCellList = new ArrayList<>();
    }

    public void setSeeReviewMode() {
        Optional<ReviewerInformation> reviewer = this.paper.getReviewerInformationList().stream().filter(reviewerInformation -> reviewerInformation.getReviewerIdentifiedName().equals(MainApp.getInstance().getUser().getReviewerIdentifiedName())).findAny();
        if (reviewer.isPresent()) {
            String reviewId = reviewer.get().getReviewId();
            try {
                Review review = reviewService.searchReviewById(reviewId);
                this.reviewContent.setText(review.getReviewContent());
                this.reviewContent.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.submitReviewPane.setVisible(false);
        this.cancelSubmitLabel.setText("Back");
        this.cancelSubmitLabel.setTextFill(Paint.valueOf("#ffffff"));
        this.cancelSubmitLabel.setStyle("-fx-background-color: #444444;-fx-background-radius: 8");
        this.cancelSubmitLabel.setPadding(new Insets(8,16,8,16));
    }
}
