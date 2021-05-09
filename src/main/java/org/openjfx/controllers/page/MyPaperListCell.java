package org.openjfx.controllers.page;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Paper;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyPaperListCell implements Initializable {
    @FXML
    private Label paperName;

    @FXML
    private Label keywords;

    @FXML
    private Label authors;

    @FXML
    private Label submittedName;

    @FXML
    private Label submittedTime;

    @FXML
    private Label paperStatus;

    @FXML
    private StackPane statusBackground;

    private Paper paper;

    private StatusButtonListener statusButtonListener;

    private PaperStatusPresentation presentation;


    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
        updateItem(paper);
    }
    protected void updateItem(Paper item) {
        paperName.setText(item.getTitle());
        keywords.setText(String.join(";", item.getKeywords()));
        authors.setText(item.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).collect(Collectors.joining(";")));
        submittedName.setText(item.getAuthors().stream().map(AuthorInformation::getAuthorDisplayName).findFirst().orElse(""));
        submittedTime.setText(item.getSubmittedTime());
        fillStatusView();
    }

    private void fillStatusView() {
        if(this.presentation == null || this.paper == null){
            return;
        }
        paperStatus.setText(this.presentation.statusText);
        statusBackground.setStyle(this.presentation.getStatusBackgroundStyle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onStatusButtonClicked(MouseEvent event){
        if(this.statusButtonListener!=null){
            this.statusButtonListener.onStatusButtonClicked(event,this.paper);
        }
    }

    public PaperStatusPresentation getPresentation() {
        return presentation;
    }

    public void setPresentation(PaperStatusPresentation presentation) {
        this.presentation = presentation;
        fillStatusView();
    }

    public StatusButtonListener getStatusButtonListener() {
        return statusButtonListener;
    }

    public void setStatusButtonListener(StatusButtonListener statusButtonListener) {
        this.statusButtonListener = statusButtonListener;
    }

    public interface StatusButtonListener{
        void onStatusButtonClicked(MouseEvent event, Paper paper);
    }

    public enum PaperStatusPresentation{
        PAPER_PAGE_SUBMITTED("Submitted","#b391b5", "Being reviewed"),
        PAPER_PAGE_ACCEPTED("Accepted","#0c7cba",""),
        PAPER_PAGE_REJECTED("Rejected","#c92d3a",""),
        PAPER_PAGE_REVIEWED("Reviewed","#19967d","Waiting for acceptance"),
        CONFERENCE_MANAGEMENT_PAPER_SUBMITTED("Assign Reviewer","#a575a8","Reviewer required"),
        CONFERENCE_MANAGEMENT_PAPER_REVIEWED("Reviewed","#19967d","Final decision required"),
        CONFERENCE_MANAGEMENT_PAPER_ACCEPTED("Accepted","#0c7cba",""),
        CONFERENCE_MANAGEMENT_PAPER_REJECTED("Rejected","#c92d3a",""),
        ;
        private final String statusText;
        private final String statusBackgroundStyle;
        private final String notificationText;

        PaperStatusPresentation(String statusText,String colourText,String notificationText){
            this.statusText = statusText;
            this.statusBackgroundStyle = String.format("-fx-background-color: %s;-fx-background-radius: 32", colourText);
            this.notificationText = notificationText;
        }

        public String getStatusText() {
            return statusText;
        }

        public String getStatusBackgroundStyle() {
            return statusBackgroundStyle;
        }

        public String getNotificationText() {
            return notificationText;
        }
    }


}
