package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.openjfx.controllers.page.abspage.AbstractFileListCell;
import org.openjfx.helper.DialogHelper;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PaperSubmitFileListCell extends AbstractFileListCell implements Initializable {

    @FXML
    private Label fileName;

    @FXML
    private ImageView fileIcon;

    @FXML
    private BorderPane topView;

    private File selectedFile;

    private OnCancelButtonSelectedListener onCancelButtonSelectedListener;

    @FXML
    void cancelSelectedFile(MouseEvent event) {
        DialogHelper.showConfirmDialog("Confirm to cancel selection?", "Delete this file?", new DialogHelper.ConfirmDialogClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                if (onCancelButtonSelectedListener != null) {
                    onCancelButtonSelectedListener.onCanceled(event, selectedFile, topView);
                }
            }
        });
    }

    private void showCancelSelectedDialog() {

    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        initViews(selectedFile);
    }

    private void initViews(File selectedFile) {
        this.fileName.setText(selectedFile.getName());
        this.fileIcon.setImage(getFileIcon(selectedFile.getAbsolutePath()));
    }

    public OnCancelButtonSelectedListener getOnCancelButtonSelectedListener() {
        return onCancelButtonSelectedListener;
    }

    public void setOnCancelButtonSelectedListener(OnCancelButtonSelectedListener onCancelButtonSelectedListener) {
        this.onCancelButtonSelectedListener = onCancelButtonSelectedListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public interface OnCancelButtonSelectedListener {
        void onCanceled(MouseEvent event, File file, Parent parent);
    }
}
