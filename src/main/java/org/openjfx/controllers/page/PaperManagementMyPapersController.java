package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaperManagementMyPapersController implements Initializable, MyPaperListCell.StatusButtonListener {

    @FXML
    private VBox myPaperContainer;

    private final PaperService paperService = PaperService.getDefaultInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getMyPaper();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    private void getMyPaper() throws IOException {
        List<Node> myPaper = paperService.getUserPapers(MainApp.getInstance().getUser()).stream().map(this::createPaperCell).filter(Objects::nonNull).collect(Collectors.toList());
        if(myPaper.size()>0){
            myPaperContainer.getChildren().setAll(myPaper);
        }

    }

    private Node createPaperCell(Paper paper) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.MY_PAPER_LIST_CELL);
            Node node = loader.load();
            MyPaperListCell cell = loader.getController();
            cell.setPaper(paper);
            cell.setStatusButtonListener(this);
            switch (paper.getPaperStatus()){
                case ACCEPTED :
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.PAPER_PAGE_ACCEPTED);
                    break;
                case REJECTED :
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.PAPER_PAGE_REJECTED);
                    break;
                case REVIEWED :
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.PAPER_PAGE_REVIEWED);
                    break;
                case SUBMITTED :
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.PAPER_PAGE_SUBMITTED);
                    break;
                case BEING_REVIEWED:
                    cell.setPresentation(MyPaperListCell.PaperStatusPresentation.PAPER_PAGE_BEING_REVIEW);
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
            case REVIEWED:
            case ACCEPTED:
            case REJECTED:
            case BEING_REVIEWED:
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
}
