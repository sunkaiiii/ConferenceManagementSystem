package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.MainApp;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;
import org.openjfx.model.interfaces.Reviewer;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReviewerAssignmentController implements Initializable, AssignReviewerReviewerListCell.OnReviewerClickListener {

    @FXML
    private Label paperName;

    @FXML
    private Label authors;

    @FXML
    private Label keywords;

    @FXML
    private VBox reviewerListContainer;

    @FXML
    private TextField reviewerFields;

    private Paper paper;

    private final UserService userService = UserService.getDefaultInstance();

    private Scene previousScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void back(MouseEvent event) throws IOException {
        if(previousScene!=null){
            SceneHelper.startStage(previousScene,event);
            return;
        }
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_PAPER, true);
    }

    @FXML
    void assignReviewer(MouseEvent event) {

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
            List<Node> reviewerListCells = userService
                    .findAllReviewers(MainApp.getInstance().getUser())
                    .stream()
                    .map(this::createReviewerListCell)
                    .collect(Collectors.toList());
            this.reviewerListContainer.getChildren().setAll(reviewerListCells);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node createReviewerListCell(Reviewer reviewer) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.ASSIGN_REVIEWER_REVIEW_LIST_CELL);
            Node result = loader.load();
            AssignReviewerReviewerListCell cell = loader.getController();
            cell.setReviewer(reviewer);
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
        this.reviewerFields.setText(this.authors.getText() + reviewer.getReviewerName() + ";");
    }
}
