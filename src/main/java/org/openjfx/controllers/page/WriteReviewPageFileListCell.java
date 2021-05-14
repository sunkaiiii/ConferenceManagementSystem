package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.controllers.page.abspage.AbstractFileListCell;
import org.openjfx.helper.FileHelper;
import org.openjfx.model.PaperFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class WriteReviewPageFileListCell extends AbstractFileListCell implements Initializable {

    @FXML
    private ImageView fileIcon;

    @FXML
    private Label fileName;

    @FXML
    private Label downloadedIndicator;

    private PaperFile paperFile;


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
        this.fileIcon.setImage(getFileIcon(paperFile.getStoragePath()));
    }

    public boolean isRead(){
        return this.downloadedIndicator.isVisible();
    }

    @FXML
    void clickToDownloadFile(MouseEvent event) throws IOException {
        assert this.paperFile != null;
        String extension = FileHelper.getInstance().getFileExtensionByStringHandling(paperFile.getStoragePath()).orElse("*");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Document format","*."+extension));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        java.io.File saveFile = fileChooser.showSaveDialog(stage);
        if(saveFile == null){
            return;
        }
        java.io.File source = new java.io.File(this.paperFile.getStoragePath());
        Files.copy(source.toPath(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.downloadedIndicator.setVisible(true);
    }
}
