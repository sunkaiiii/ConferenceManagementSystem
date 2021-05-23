package org.openjfx.controllers.page;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.MainApp;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.AddKeywordDialog;
import org.openjfx.helper.*;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.model.PaperFile;
import org.openjfx.model.interfaces.Author;
import org.openjfx.service.PaperService;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SubmitPaperController implements Initializable, PreDefineListCellController.OnKeywordSelectedListener, PaperSubmitFileListCell.OnCancelButtonSelectedListener {

    @FXML
    private AutoTrimTextField paperName;

    @FXML
    private Label conferenceName;

    @FXML
    private Label chairName;

    @FXML
    private Parent selectPaperContainer;

    @FXML
    private StackPane selectPaperParentView;

    @FXML
    private MenuButton authorSelectMenuButton;

    @FXML
    private TextField keywordField;

    @FXML
    private TextField authorField;

    @FXML
    private FlowPane preDefineKeywordFlowPane;

    @FXML
    private VBox fileListContainer;

    @FXML
    private Parent folderContainer;

    private Conference conference;

    private List<Author> authorList;

    private List<Author> selectedAuthor;

    private List<TextField> allFields;

    private Set<java.io.File> paperFiles;

    List<String> selectedKeywords;

    List<PreDefineListCellController> preDefineListCellControllers;

    private final PaperService paperService = PaperService.getDefaultInstance();

    private final EventHandler<MouseEvent> selectPaperEvent = this::selectPapers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFields = List.of(paperName, keywordField);
        selectedAuthor = new ArrayList<>(List.of(MainApp.getInstance().getUser()));
        paperFiles = new HashSet<>();
        selectedKeywords = new ArrayList<>();
        preDefineListCellControllers = new ArrayList<>();
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
        List<MenuItem> items = authorList
                .stream()
                .map(this::createMenuItem)
                .collect(Collectors.toList());
        this.authorSelectMenuButton.getItems().setAll(items);
        this.preDefineKeywordFlowPane.getChildren().addAll(getPreDefineKeywordsCells());
        this.selectPaperParentView.addEventHandler(MouseEvent.MOUSE_CLICKED, selectPaperEvent);
    }

    private void initAuthorList() {
        try {
            authorList = UserService.getDefaultInstance().findAuthors();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuItem createMenuItem(Author author) {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(author.getAuthorName());
        menuItem.setUserData(author);
        menuItem.setOnAction((event) -> authorItemSelected(event, menuItem));
        return menuItem;
    }

    private void authorItemSelected(ActionEvent event, MenuItem item) {
        Author author = (Author) item.getUserData();
        authorField.setText(authorField.getText() + author.getAuthorName() + ";");
        selectedAuthor.add(author);
        this.authorSelectMenuButton.getItems().remove(item);
    }

    private List<Parent> getPreDefineKeywordsCells() {
        List<String> preDefineKeywords = PreDefineKeywordHelper.getPreDefineList();
        return preDefineKeywords.stream().map(this::createKeywordCell).collect(Collectors.toList());
    }

    private Parent createKeywordCell(String keyword) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.PRE_DEFINE_KEYWORD_CELL);
            Parent result = loader.load();
            PreDefineListCellController cell = loader.getController();
            preDefineListCellControllers.add(cell);
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
        if (paperFiles.size() > 0) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Paper format (*.pdf, *.doc, *.docx)", "*.doc", "*.docx", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        List<java.io.File> selectedFiles = fileChooser.showOpenMultipleDialog(appStage);
        if (selectedFiles == null || selectedFiles.size() == 0) {
            return;
        }
        this.paperFiles.addAll(selectedFiles);
        refreshFilePageCell();
    }

    private void refreshFilePageCell() {
        this.selectPaperParentView.removeEventHandler(MouseEvent.MOUSE_CLICKED, selectPaperEvent);
        List<Node> result = this.paperFiles.stream().map(this::createFileListCell).filter(Objects::nonNull).collect(Collectors.toList());
        this.fileListContainer.getChildren().setAll(result);
        this.folderContainer.setVisible(!(result.size() > 0));
        if (result.size() == 0) {
            this.selectPaperParentView.addEventHandler(MouseEvent.MOUSE_CLICKED, selectPaperEvent);
        }

    }

    private Node createFileListCell(java.io.File file) {
        try {
            FXMLLoader loader = SceneHelper.createViewWithResourceName(getClass(), PageNames.PAPER_SUBMIT_FILE_LIST_CELL);
            Node result = loader.load();
            PaperSubmitFileListCell cell = loader.getController();
            cell.setSelectedFile(file);
            cell.setOnCancelButtonSelectedListener(this);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void showAddKeywordDialog(MouseEvent event) throws IOException {
        SceneHelper.showDialogStage(getClass(), 300, 150, PageNames.ADD_KEYWORD_DIALOG, (AddKeywordDialog dialog) -> {
            dialog.setOnAddKeywordListener(this::addKeywordToField);
        });
    }

    private void addKeywordToField(String newKeyword) {
        if (this.selectedKeywords.stream().anyMatch(keyword -> keyword.equalsIgnoreCase(newKeyword))) {
            DialogHelper.showErrorDialog("There is already have a keyword of " + newKeyword);
            return;
        }
        this.selectedKeywords.add(newKeyword);
        this.preDefineListCellControllers.stream().filter(cell->cell.getKeyword().equalsIgnoreCase(newKeyword)).forEach(cell->cell.setState(PreDefineListCellController.SelectedState.DELETE));
        this.keywordField.setText(String.join(";", this.selectedKeywords));
    }

    private void removeKeywordToField(String keyword) {
        String ignoreCaseKeyword = this.selectedKeywords.stream().filter(k -> k.equalsIgnoreCase(keyword)).findAny().orElse("");
        if (!ignoreCaseKeyword.isBlank()) {
            this.selectedKeywords.remove(ignoreCaseKeyword);
        }
        this.preDefineListCellControllers.stream().filter(cell->cell.getKeyword().equalsIgnoreCase(keyword)).forEach(cell->cell.setState(PreDefineListCellController.SelectedState.ADD));
        this.keywordField.setText(String.join(";", this.selectedKeywords));
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
        DialogHelper.showConfirmDialog("", "Do you want to submit these paper(s)?", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                submitPaperToSystem(event);
            }
        });


    }

    private void submitPaperToSystem(MouseEvent event) {
        List<PaperFile> paperFiles = this.paperFiles.stream().map(paper -> {
            try {
                return FileHelper.getInstance().uploadFileToServer(paper.getName(), paper.getAbsolutePath());
            } catch (IOException e) {
                DialogHelper.showErrorDialog("Upload file error");
                e.printStackTrace();
                throw new RuntimeException();
            }
        }).collect(Collectors.toList());
        Paper paper = new Paper(paperName.getTrimText(), conference.getTopic(), this.selectedKeywords, conference.getDeadline(), paperFiles, conference.getId());
        try {
            paperService.submitPaper(selectedAuthor, paper);
            SceneHelper.startPage(getClass(), event, PageNames.PAPER_MANAGEMENT, true);
            SceneHelper.deleteScene(PageNames.CONFERENCE_MANAGEMENT.getPageName());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onKeywordSelected(String keyword, PreDefineListCellController.SelectedState state) {
        if (state == PreDefineListCellController.SelectedState.ADD) {
            addKeywordToField(keyword);
        } else if (state == PreDefineListCellController.SelectedState.DELETE) {
            removeKeywordToField(keyword);
        }
    }

    public boolean validate() {
        if (InputValidation.checkTextFiledIsEmpty(this.allFields)) {
            DialogHelper.showErrorDialog("You need to fill up all fields");
            return false;
        }
        if (this.paperFiles == null || this.paperFiles.isEmpty()) {
            DialogHelper.showErrorDialog("You need to choose paper(s)");
            return false;
        }

        if (this.keywordField.getText().split(";").length < 3) {
            DialogHelper.showErrorDialog("You need to input at least 3 keywords");
            return false;
        }
        return true;
    }

    @Override
    public void onCanceled(MouseEvent event, java.io.File file, Parent parent) {
        this.paperFiles.remove(file);
        event.consume();
        refreshFilePageCell();
    }
}
