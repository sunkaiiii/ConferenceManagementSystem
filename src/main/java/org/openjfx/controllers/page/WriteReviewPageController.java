package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.AutoTrimTextField;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;
import org.openjfx.model.PaperFile;
import org.openjfx.model.Review;
import org.openjfx.service.ConferenceService;
import org.openjfx.service.ReviewService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
        DialogHelper.showConfirmDialog("Confirmation", "Do you want to submit this review?", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                try {
                    writeReview(event, review);
                    backToPreviousPageAndRefresh(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
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
}
