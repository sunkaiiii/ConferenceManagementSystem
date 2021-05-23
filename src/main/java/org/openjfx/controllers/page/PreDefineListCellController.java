package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Predefined keyword cell, when the user clicks on this cell, the layout of the cell will change and the keyword will be added or removed via a callback
 */
public class PreDefineListCellController implements Initializable {

    @FXML
    private Label statusLabel;

    @FXML
    private Label keywordDesc;

    @FXML
    private HBox cellBody;

    private String keyword;

    private SelectedState state = SelectedState.ADD;

    private OnKeywordSelectedListener onKeywordSelectedListener;

    private final String nonSelectedBorderColour = "#7AB648";
    private final String nonSelectedBackgroundColour = "#C7E8AC";
    private final String nonSelectedTextColour = "#19967D";
    private final String nonSelectedLabelIndicator = "+ ";
    private final String selectedBorderColour = "#C92D39";
    private final String selectedBackgroundColour = "#F5B5C8";
    private final String selectedTextColour = selectedBorderColour;
    private final String selectedLabelIndicator = "- ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        initView();
    }

    private void initView() {
        switch (this.state) {
            case ADD:
                tintViews(nonSelectedBorderColour, nonSelectedBackgroundColour, nonSelectedTextColour, nonSelectedLabelIndicator);
                break;
            case DELETE:
                tintViews(selectedBorderColour, selectedBackgroundColour, selectedTextColour, selectedLabelIndicator);
                break;
            default:
                break;
        }
        this.keywordDesc.setText(keyword);
    }

    private void tintViews(String borderColour, String backgroundColour, String textColour, String indicator) {
        String style = String.format("-fx-background-color: %s;-fx-border-color: %s; -fx-background-radius: 8; -fx-border-radius: 8", backgroundColour, borderColour);
        this.cellBody.setStyle(style);
        this.statusLabel.setText(indicator);
        this.statusLabel.setTextFill(Paint.valueOf(textColour));
        this.keywordDesc.setTextFill(Paint.valueOf(textColour));

    }

    @FXML
    void keywordClicked(MouseEvent event) throws IOException {
        SelectedState currentState = this.state;
        if (this.state == SelectedState.ADD) {
            this.state = SelectedState.DELETE;
        } else {
            this.state = SelectedState.ADD;
        }
        if (onKeywordSelectedListener != null) {
            onKeywordSelectedListener.onKeywordSelected(this.keyword, currentState);
        }
        initView();
    }

    public SelectedState getState() {
        return state;
    }

    public void setState(SelectedState state) {
        this.state = state;
        initView();
    }

    public void setOnKeywordSelectedListener(OnKeywordSelectedListener onKeywordSelectedListener) {
        this.onKeywordSelectedListener = onKeywordSelectedListener;
    }

    /**
     * This interface must be implemented until the keyword is selected when the click event is triggered
     */
    public interface OnKeywordSelectedListener {
        void onKeywordSelected(String keyword, SelectedState state);
    }


    public enum SelectedState {
        ADD,
        DELETE
    }
}
