package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.openjfx.MainApp;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.service.PaperService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaperManagementMyPapersController implements Initializable {

    @FXML
    private VBox myPaperContainer;

    private final PaperService paperService = PaperService.getInstance();

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
        myPaperContainer.getChildren().setAll(myPaper);
    }

    private Node createPaperCell(Paper paper) {
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
