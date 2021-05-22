package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ReviewManagementController implements Initializable {

    @FXML
    private FlowPane reviewPaperList;

    @FXML
    private FlowPane reviewedPaperList;

    @FXML
    private Label reviewedPaperLabel;

    @FXML
    private Label assignToYouLabel;

    @FXML
    private Label noContentLabel;

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
                    .map(this::createBeingReviewCell)
                    .filter(Objects::nonNull).collect(Collectors.toList());
            List<Node> reviewedPaperCells = paperService.findPaperReviewedByTheUser(MainApp.getInstance().getUser())
                    .stream()
                    .map(this::createReviewedCell)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            this.assignToYouLabel.setVisible(paperCells.size() > 0);
            this.reviewedPaperLabel.setVisible(reviewedPaperCells.size() > 0);
            this.reviewPaperList.setVisible(paperCells.size() > 0);
            this.reviewedPaperList.setVisible(reviewedPaperCells.size() > 0);
            this.reviewPaperList.getChildren().setAll(paperCells);
            this.reviewedPaperList.getChildren().setAll(reviewedPaperCells);
            this.noContentLabel.setVisible(paperCells.size() == 0 && reviewedPaperCells.size() == 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node loadReviewCellResource(Paper paper, Consumer<ReviewPageCell> cellConsumer) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.REVIEW_PAGE_CELL);
            Node result = loader.load();
            ReviewPageCell cell = loader.getController();
            cellConsumer.accept(cell);
            cell.setPaper(paper);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Node createBeingReviewCell(Paper paper) {
        return loadReviewCellResource(paper, cell -> {
            cell.setCellType(ReviewPageCell.CellType.BEING_REVIEW);
        });
    }

    private Node createReviewedCell(Paper paper) {
        return loadReviewCellResource(paper, cell -> {
            cell.setCellType(ReviewPageCell.CellType.REVIEWED);
        });
    }


}
