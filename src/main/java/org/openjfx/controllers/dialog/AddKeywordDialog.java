package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.openjfx.helper.AutoTrimTextField;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;

public class AddKeywordDialog {
    @FXML
    private AutoTrimTextField keyword;

    private OnAddKeywordListener onAddKeywordListener;

    @FXML
    private void closeDialog(MouseEvent event) {
        Stage stage = (Stage) keyword.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addKeyword(MouseEvent event) {
        if (InputValidation.checkTextFiledIsEmpty(keyword)) {
            DialogHelper.showErrorDialog("You need input a keyword");
            return;
        }
        if (this.onAddKeywordListener != null) {
            this.onAddKeywordListener.onKeywordAdded(keyword.getTrimText());
        }
        closeDialog(event);
    }

    public OnAddKeywordListener getOnAddKeywordListener() {
        return onAddKeywordListener;
    }

    public void setOnAddKeywordListener(OnAddKeywordListener onAddKeywordListener) {
        this.onAddKeywordListener = onAddKeywordListener;
    }

    public interface OnAddKeywordListener {
        void onKeywordAdded(String keyword);
    }
}
