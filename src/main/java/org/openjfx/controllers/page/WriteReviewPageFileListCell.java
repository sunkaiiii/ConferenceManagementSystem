package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.helper.FileHelper;
import org.openjfx.model.PaperFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;

public class WriteReviewPageFileListCell implements Initializable {

    public static final String WORD_RESOURCE = "/org/openjfx/images/word.png";
    public static final String PNG_RESOURCE = "/org/openjfx/images/pdf.png";
    @FXML
    private ImageView fileIcon;

    @FXML
    private Label fileName;

    @FXML
    private Label downloadedIndicator;

    private PaperFile paperFile;

    private static final String WORD_FORMAT_OLD = "doc";
    private static final String WORD_FORMAT = "docx";
    private static final String PDF_FORMAT ="pdf";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public PaperFile getPaperFile() {
        return paperFile;
    }

    public void setPaperFile(PaperFile paperFile) {
        this.paperFile = paperFile;
        initViews(paperFile);
    }

    private void initViews(PaperFile paperFile) {
        this.fileName.setText(paperFile.getFileName());
        initFileIcon(paperFile);
    }

    private void initFileIcon(PaperFile paperFile) {
        Optional<String> fileExtension = FileHelper.getInstance().getFileExtensionByStringHandling(paperFile.getStoragePath());
        if(fileExtension.isPresent()){
            switch (fileExtension.get()){
                case WORD_FORMAT:
                case WORD_FORMAT_OLD:
                    this.fileIcon.setImage(new Image(getClass().getResource(WORD_RESOURCE).toString()));
                    break;
                case PDF_FORMAT:
                    this.fileIcon.setImage(new Image(getClass().getResource(PNG_RESOURCE).toString()));
                    break;
            }
        }
    }

    @FXML
    void clickToDownloadFile(MouseEvent event) throws IOException {
        assert this.paperFile != null;
        String extension = FileHelper.getInstance().getFileExtensionByStringHandling(paperFile.getStoragePath()).orElse("*");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Document format","*."+extension));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File saveFile = fileChooser.showSaveDialog(stage);
        File source = new File(this.paperFile.getStoragePath());
        Files.copy(source.toPath(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.downloadedIndicator.setVisible(true);
    }
}
