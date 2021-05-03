package org.openjfx.controllers.page;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.MainApp;
import org.openjfx.helper.FileHelper;
import org.openjfx.helper.InputValidation;
import org.openjfx.helper.PreDefineKeywordHelper;
import org.openjfx.helper.SceneHelper;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.PaperFile;
import org.openjfx.model.datamodel.interfaces.Author;
import org.openjfx.service.PaperService;
import org.openjfx.service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
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

    private List<Author> authorList;

    private List<Author> selectedAuthor;

    private List<TextField> allFields;

    private List<File> paperFiles;

    private final PaperService paperService = PaperService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFields = List.of(paperName, keywordField);
        selectedAuthor = List.of(MainApp.getInstance().getUser());
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
            //TODO rewrite observable value item
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                var listener = this;
                authorSelectBox.valueProperty().removeListener(listener);
                authorField.setText(authorField.getText() + newValue + ";");
                authorSelectBox.getItems().remove(newValue);
                //TODO user identify name
                selectedAuthor.add(authorList.stream().filter(author -> author.getAuthorName().equals(newValue)).findAny().get());
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
        //TODO filter does not work?
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Paper format", ".doc", ".docx", ".pdf");
        fileChooser.setSelectedExtensionFilter(filter);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.paperFiles = fileChooser.showOpenMultipleDialog(appStage);
        ;
        String fileNames = this.paperFiles.stream().map(File::getName).collect(Collectors.joining("\n"));
        fileName.setText(fileNames);
    }

    @FXML
    void cancelSubmit(MouseEvent event) throws IOException {
        SceneHelper.startPage(getClass(), event, PageNames.PAPER_MANAGEMENT, true);
    }

    @FXML
    void submitPaper(MouseEvent event) throws IOException {
        if (!validate()) {
            return;
        }
        List<PaperFile> paperFiles = this.paperFiles.stream().map(paper -> {
            try {
                return FileHelper.getInstance().uploadFileToServer(paper.getName(), paper.getAbsolutePath());
            } catch (IOException e) {
                InputValidation.showErrorDialog("Upload file error");
                System.out.println(e);
                throw new RuntimeException();
            }
        }).collect(Collectors.toList());
        Paper paper = new Paper(paperName.getText(), conference.getTopic(), Arrays.stream(keywordField.getText().split(";")).collect(Collectors.toList()), conference.getDeadline(), paperFiles, conference.getName());
        paperService.submitPaper(selectedAuthor, paper);
        //TODO second confirmation dialog



        SceneHelper.startPage(getClass(),event,PageNames.PAPER_MANAGEMENT,true);
    }

    @Override
    public void onKeywordSelected(String keyword, PreDefineListCellController.SelectedState state) {
        if (state == PreDefineListCellController.SelectedState.ADD) {
            this.keywordField.setText(this.keywordField.getText() + keyword + ";");
        } else if (state == PreDefineListCellController.SelectedState.DELETE) {
            this.keywordField.setText(this.keywordField.getText().replace(keyword + ";", ""));
        }
    }

    public boolean validate() {
        if (InputValidation.checkTextFiledIsEmpty(this.allFields)) {
            InputValidation.showErrorDialog("You need to fill up all fields");
            return false;
        }
        if (this.paperFiles == null || this.paperFiles.isEmpty()) {
            InputValidation.showErrorDialog("You need to choose paper(s)");
            return false;
        }

        if (this.keywordField.getText().split(";").length < 3) {
            InputValidation.showErrorDialog("You need to input at least 3 keywords");
            return false;
        }
        return true;
    }
}
