package org.openjfx.model;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.interfaces.CSVConvertable;

import java.util.UUID;

public class Review implements CSVConvertable<Review> {
    private String id;
    private String reviewContent;
    private String deadLine;
    private String reviewerIdentifiedName;
    private String paperId;

    public Review(String paperId,String reviewContent, String reviewerIdentifiedName) {
        this.id = UUID.randomUUID().toString();
        this.paperId = paperId;
        this.reviewContent = reviewContent;
        this.reviewerIdentifiedName = reviewerIdentifiedName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }


    public String getReviewerIdentifiedName() {
        return reviewerIdentifiedName;
    }

    public void setReviewerIdentifiedName(String reviewerIdentifiedName) {
        this.reviewerIdentifiedName = reviewerIdentifiedName;
    }


    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }
}
