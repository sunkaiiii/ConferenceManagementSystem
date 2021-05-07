package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConferencePaperController implements Initializable {
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


    private Node createPaperCell(Paper paper){
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.MY_PAPER_LIST_CELL);
            Node node = loader.load();
            MyPaperListCell cell = loader.getController();
            cell.setPaper(paper);
            return node;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
