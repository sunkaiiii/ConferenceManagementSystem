package org.openjfx.controllers.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.openjfx.controllers.PageNames;
import org.openjfx.controllers.dialog.absdialog.AbstractAlertDialog;
import org.openjfx.helper.SceneHelper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class FinalDecisionAlertView extends AbstractAlertDialog {

    @FXML
    private Button negativeButton;

    @FXML
    private Button positiveButton;

    @FXML
    private Label alertContent;

    @FXML
    private Parent contentBody;

    private Parent root;



    private WeakReference<Node> callerReference;

    public FinalDecisionAlertView() {
        try {
            FXMLLoader fxmlLoader = SceneHelper.createViewWithResourceName(getClass(), PageNames.FINAL_DECISION_ALERT_VIEW);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            this.root = fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void negativeButtonClicked(MouseEvent event) {
        actionButtonClicked(event, getAlertDialogClickListener()::onNegativeButtonClick);
    }

    @FXML
    void positiveButtonClicked(MouseEvent event) {
        actionButtonClicked(event, getAlertDialogClickListener()::onPositiveButtonClick);
    }

    private void actionButtonClicked(MouseEvent event, Consumer<MouseEvent> mouseEventConsumer) {
        if (getAlertDialogClickListener() != null) {
            mouseEventConsumer.accept(event);
        }
        dismiss();
    }


    @Override
    public void setAlertContent(String content) {
        this.alertContent.setText(content);
    }

    @Override
    public Node getRoot() {
        return this.root;
    }

    @Override
    public Node getContentBody() {
        return this.contentBody;
    }

    @Override
    public Button getPositiveButton() {
        return this.positiveButton;
    }

    @Override
    public Button getNegativeButton() {
        return this.negativeButton;
    }


}
