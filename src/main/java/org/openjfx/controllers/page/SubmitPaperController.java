package org.openjfx.controllers.page;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.helper.PreDefineKeywordHelper;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.interfaces.Author;
import org.openjfx.service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SubmitPaperController implements Initializable, PreDefineListCellController.OnKeywordSelectedListener {

    @FXML
    private TextField paperName;

    @FXML
    private Label conferenceName;

    @FXML
    private Label chairName;

    @FXML
    private Label fileName;

    @FXML
    private ComboBox<String> authorSelectBox;

    @FXML
    private TextField keywordField;

    @FXML
    private TextField authorField;

    @FXML
    private FlowPane preDefineKeywordFlowPane;

    private Conference conference;

    List<Author> authorList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
        initPage(conference);
    }

    private void initPage(Conference conference) {
        this.chairName.setText(conference.getChairName());
        this.conferenceName.setText(conference.getName());
        initAuthorList();
        this.authorSelectBox.setItems(FXCollections.observableArrayList(authorList.stream().map(Author::getAuthorName).collect(Collectors.toList())));
        this.authorSelectBox.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                var listener = this;
                authorSelectBox.valueProperty().removeListener(listener);
                authorField.setText(authorField.getText() + newValue + ";");
                authorSelectBox.getItems().remove(newValue);
                authorSelectBox.valueProperty().addListener(listener);
            }
        });
        this.preDefineKeywordFlowPane.getChildren().addAll(getPreDefineKeywordsCells());
    }

    private void initAuthorList() {
        try {
            authorList = UserService.getInstance().findAuthors();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Parent> getPreDefineKeywordsCells() {
        List<String> preDefineKeywords = PreDefineKeywordHelper.getPreDefineList();
        return preDefineKeywords.stream().map(this::createKeywordCell).collect(Collectors.toList());
    }

    private Parent createKeywordCell(String keyword) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.PRE_DEFINE_KEYWORD_CELL.getPageName());
            Parent result = loader.load();
            PreDefineListCellController cell = loader.getController();
            cell.setKeyword(keyword);
            cell.setOnKeywordSelectedListener(this);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void selectPapers(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Paper format", ".doc", ".docx", ".pdf");
        fileChooser.setSelectedExtensionFilter(filter);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(appStage);
        String fileNames = selectedFiles.stream().map(File::getName).collect(Collectors.joining("\n"));
        fileName.setText(fileNames);
    }

    @FXML
    void cancelSubmit(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.PAPER_MANAGEMENT, true);
    }

    @Override
    public void onKeywordSelected(String keyword, PreDefineListCellController.SelectedState state) {
        if (state == PreDefineListCellController.SelectedState.ADD) {
            this.keywordField.setText(this.keywordField.getText() + keyword + ";");
        } else if (state == PreDefineListCellController.SelectedState.DELETE) {
            this.keywordField.setText(this.keywordField.getText().replace(keyword + ";", ""));
        }
    }
}
