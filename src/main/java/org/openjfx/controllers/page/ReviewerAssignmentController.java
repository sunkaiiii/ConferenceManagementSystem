package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.GeneralAlertView;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.SceneHelper;
import org.openjfx.helper.ViewHelper;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;
import org.openjfx.model.interfaces.Reviewer;
import org.openjfx.service.PaperService;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The page where reviewers are assigned, showing a list of all reviewers except the author
 */
public class ReviewerAssignmentController implements Initializable, AssignReviewerReviewerListCell.OnReviewerClickListener {

    @FXML
    private Label paperName;

    @FXML
    private Label authors;

    @FXML
    private Label keywords;

    @FXML
    private BorderPane rootView;

    @FXML
    private VBox reviewerListContainer;

    @FXML
    private FlowPane selectedReviewersPane;

    @FXML
    private GeneralAlertView assignReviewerConfirmationView;

    private Paper paper;

    private final UserService userService = UserService.getDefaultInstance();

    private Scene previousScene;

    private List<Reviewer> selectedReviewer;

    private final PaperService paperService = PaperService.getDefaultInstance();

    private OnReviewerAssignedListener onReviewerAssignedListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedReviewer = new ArrayList<>();
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
        this.authors.setText(paper.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        this.keywords.setText(String.join(";", paper.getKeywords()));
        try {
            //Grouping reviewers, sorting them by expertise and removing users who are authors,
            // and eventually converting them into displayable cells with an emphasis on reviewers with the same professional interests and thesis keywords
            List<Node> reviewerListCells = userService
                    .findAllReviewers(MainApp.getInstance().getUser())
                    .stream()
                    .filter(this::reviewerIsNotAuthor)
                    .sorted(Comparator.comparingInt(this::getLevelExpertise))
                    .collect(Collectors.groupingBy(this::getReviewerInterestAreaName))
                    .values()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(this::createReviewerListCell)
                    .collect(Collectors.toList());
            this.reviewerListContainer.getChildren().setAll(reviewerListCells);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.reviewerListContainer.setBorder(ViewHelper.createGeneralDashBolder("#a5b9ff"));
    }

    private int getLevelExpertise(Reviewer reviewer){
        final var expertise =  reviewer.getInterestAreas().values().stream().findFirst();
        return expertise.orElse(Integer.MIN_VALUE);
    }

    private String getReviewerInterestAreaName(Reviewer reviewer){
        final var interestArea = reviewer.getInterestAreas().keySet().stream().findFirst();
        return interestArea.orElse("Unknown");
    }

    private boolean reviewerIsNotAuthor(Reviewer reviewer) {
        return this.paper.getAuthors().stream().noneMatch(author -> author.getAuthorIdentifyName().equals(reviewer.getReviewerIdentifiedName()));
    }

    private Node createReviewerListCell(Reviewer reviewer) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.ASSIGN_REVIEWER_REVIEW_LIST_CELL);
            Node result = loader.load();
            AssignReviewerReviewerListCell cell = loader.getController();
            cell.setReviewer(reviewer);
            if (reviewer.getInterestAreas().keySet().stream().anyMatch(area -> this.paper.getKeywords().stream().anyMatch(keywords -> keywords.equalsIgnoreCase(area)))) {
                cell.setRecommend();
            }
            cell.setOnCellClickedListener(this);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    @Override
    public void onClick(MouseEvent event, Reviewer reviewer) {
        this.selectedReviewersPane.getChildren().add(createSelectedReviewerNode(event, reviewer));
        Node node = (Node) event.getSource();
        this.reviewerListContainer.getChildren().stream().filter(n -> n == node).findAny().ifPresent(value -> this.reviewerListContainer.getChildren().remove(value));
        this.selectedReviewer.add(reviewer);
    }

    private Node createSelectedReviewerNode(MouseEvent event, Reviewer reviewer) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.SELECTED_REVIEWER_CELL);
            Node result = loader.load();
            SelectedReviewerCell controller = loader.getController();
            controller.setReviewer(reviewer);
            controller.setOnCancelClickedListener(this::onCancelReviewerClick);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onCancelReviewerClick(MouseEvent event, Reviewer reviewer) {
        this.selectedReviewersPane.getChildren().remove(event.getSource());
        reviewerListContainer.getChildren().add(createReviewerListCell(reviewer));
        this.selectedReviewer.remove(reviewer);
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        if (previousScene != null) {
            SceneHelper.startStage(previousScene, event);
            return;
        }
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_PAPER, true);
    }

    @FXML
    void assignReviewer(MouseEvent event) {
        if (!validation()) {
            return;
        }
        assignReviewerConfirmationView.setAlertContent("Do you want to assign those reviewer?\n" + this.selectedReviewer.stream().map(Reviewer::getReviewerName).collect(Collectors.joining("\n")));
        assignReviewerConfirmationView.setAlertDialogClickListener(new AbstractAlertDialog.AlertDialogClickListener() {
            @Override
            public void onPositiveButtonClick(MouseEvent e) {
                setReviewer(event);
            }
        });
        assignReviewerConfirmationView.show(rootView, 400);
    }

    private boolean validation() {
        if (this.selectedReviewer.size() < 3 || this.selectedReviewer.size() > 4) {
            DialogHelper.showErrorDialog("The number of reviewers needs to be 3 or 4");
            return false;
        }
        return true;
    }

    private void setReviewer(MouseEvent event) {
        try {
            paperService.setReviewer(paper, selectedReviewer);
            if (this.onReviewerAssignedListener != null) {
                this.onReviewerAssignedListener.onReviewerAssigned();
            }
            back(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OnReviewerAssignedListener getOnReviewerAssignedListener() {
        return onReviewerAssignedListener;
    }

    public void setOnReviewerAssignedListener(OnReviewerAssignedListener onReviewerAssignedListener) {
        this.onReviewerAssignedListener = onReviewerAssignedListener;
    }

    public interface OnReviewerAssignedListener {
        void onReviewerAssigned();
    }
}
