package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * Show all papers submitted under this Conference
 */
public class ConferencePaperController implements Initializable, MyPaperListCell.StatusButtonListener {
    @FXML
    private Label conferenceName;

    @FXML
    private VBox paperToBeProcessedTabContainer;

    @FXML
    private VBox processedPaperTabContainer;


    private Conference conference;

    private final PaperService paperService = PaperService.getDefaultInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
        initView(conference);
    }

    private void initView(final Conference conference) {
        conferenceName.setText(conference.getName());
        try {
            List<Paper> conferencePaper = paperService.getConferencePaper(conference);
            List<Node> paperToBeProcessed = conferencePaper.stream().filter(paper -> !paper.isProcessed()).map(this::createPaperCell).collect(Collectors.toList());
            List<Node> processedPaper = conferencePaper.stream().filter(Paper::isProcessed).map(this::createPaperCell).collect(Collectors.toList());
            paperToBeProcessedTabContainer.getChildren().setAll(paperToBeProcessed);
            processedPaperTabContainer.getChildren().setAll(processedPaper);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    private Node createPaperCell(Paper paper) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.MY_PAPER_LIST_CELL);
            Node node = loader.load();
            MyPaperListCell cell = loader.getController();
            cell.setPaper(paper);
            cell.setStatusButtonListener(this);

            //Display different styles of buttons depending on the status of the paper
            switch (paper.getPaperStatus()) {
                case ACCEPTED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.CONFERENCE_MANAGEMENT_PAPER_ACCEPTED);
                    break;
                case REJECTED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.CONFERENCE_MANAGEMENT_PAPER_REJECTED);
                    break;
                case REVIEWED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.CONFERENCE_MANAGEMENT_PAPER_REVIEWED);
                    break;
                case SUBMITTED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.CONFERENCE_MANAGEMENT_PAPER_SUBMITTED);
                    break;
                case BEING_REVIEWED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.CONFERENCE_MANAGEMENT_PAPER_BEING_REVIEW);
                    break;
            }
            return node;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void onStatusButtonClicked(MouseEvent event, Paper paper) {
        switch (paper.getPaperStatus()) {
            case SUBMITTED:
                goToReviewerAssignmentPage(event, paper);
                break;
            case REVIEWED:
                goToFinalDecisionPage(event, paper);
                break;
            case ACCEPTED:
            case REJECTED:
                goToFinalDecisionPageWithoutEditing(event,paper);
            default:
                break;
        }
    }

    private void goToFinalDecisionPageWithoutEditing(MouseEvent event, Paper paper) {
        try {
            SceneHelper.startPage(getClass(), event, PageNames.PAPER_FINAL_DECISION_PAGE, false, (PaperFinalDecisionPageController controller) -> {
                controller.setPaper(paper);
                controller.setPreviousScene(((Node) event.getSource()).getScene());
                controller.setViewOnlyMode();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToFinalDecisionPage(MouseEvent event, Paper paper) {
        try {
            SceneHelper.startPage(getClass(), event, PageNames.PAPER_FINAL_DECISION_PAGE, false, (PaperFinalDecisionPageController controller) -> {
                controller.setPaper(paper);
                controller.setPreviousScene(((Node) event.getSource()).getScene());
                controller.setFinalDecisionAppliedListener(() -> initView(this.conference));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToReviewerAssignmentPage(MouseEvent event, Paper paper) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.REVIEWER_ASSIGNMENT);
            Parent node = loader.load();
            ReviewerAssignmentController controller = loader.getController();
            controller.setPaper(paper);
            controller.setPreviousScene(((Node) event.getSource()).getScene());
            controller.setOnReviewerAssignedListener(() -> initView(this.conference));
            SceneHelper.startStage(new Scene(node), event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void backToPreviousView(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.CONFERENCE_MANAGEMENT, true);
    }
}
