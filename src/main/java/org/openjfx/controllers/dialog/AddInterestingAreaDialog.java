package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.openjfx.helper.AutoTrimTextField;
import org.openjfx.helper.DialogHelper;
import org.openjfx.helper.InputValidation;

import java.util.List;

public class AddInterestingAreaDialog {
    @FXML
    private AutoTrimTextField interestingAreaName;

    @FXML
    private AutoTrimTextField interestingAreaExpertise;

    private OnAddInterestingAreaListener onAddInterestingAreaListener;

    @FXML
    void btnAddInterestingAreaClicked(MouseEvent event) {
        if (InputValidation.checkTextFiledIsEmpty(List.of(interestingAreaName, interestingAreaExpertise))) {
            DialogHelper.showErrorDialog("You need to input name and level of expertise");
            return;
        }
        int expertise = Integer.MIN_VALUE;
        try {
            expertise = Integer.parseInt(interestingAreaExpertise.getTrimText());
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.showErrorDialog("The expertise needs to be a number (0-10)");
            return;
        }
        if (expertise < 0 || expertise > 10) {
            DialogHelper.showErrorDialog("The expertise needs to be a number (0-10)");
            return;
        }
        if (onAddInterestingAreaListener != null) {
            onAddInterestingAreaListener.onAdded(new Pair<>(interestingAreaName.getTrimText(), expertise));
        }
        closeStage(event);
    }

    private void closeStage(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public OnAddInterestingAreaListener getOnAddInterestingAreaListener() {
        return onAddInterestingAreaListener;
    }

    public void setOnAddInterestingAreaListener(OnAddInterestingAreaListener onAddInterestingAreaListener) {
        this.onAddInterestingAreaListener = onAddInterestingAreaListener;
    }

    public interface OnAddInterestingAreaListener {
        void onAdded(Pair<String, Integer> newInterestingArea);
    }
}
