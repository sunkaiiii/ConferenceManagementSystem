package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import org.openjfx.MainApp;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReviewManagementController implements Initializable {

    @FXML
    private FlowPane reviewPaperList;

    private final PaperService paperService = PaperService.getDefaultInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initViews();
    }

    private void initViews() {
        try {
            List<Node> paperCells = paperService
                    .findPaperNeedToBeReviewedByTheUser(MainApp.getInstance().getUser())
                    .stream()
                    .map(this::createReviewPaperCell)
                    .filter(Objects::nonNull).collect(Collectors.toList());
            this.reviewPaperList.getChildren().setAll(paperCells);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node createReviewPaperCell(Paper paper) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.REVIEW_PAGE_CELL);
            Node result = loader.load();
            ReviewPageCell cell = loader.getController();
            cell.setPaper(paper);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
