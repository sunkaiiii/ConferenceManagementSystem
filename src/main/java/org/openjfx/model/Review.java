package org.openjfx.model;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.interfaces.CSVConvertable;

import java.util.UUID;

public class Review implements CSVConvertable<Review> {
    private String id;
    private String reviewContent;
    private String deadLine;
    private String reviewerIdentifiedName;
    private String reviewerDisplayName;
    private String paperId;

    public Review(String paperId, String reviewContent, String reviewerIdentifiedName, String reviewerDisplayName) {
        this.id = UUID.randomUUID().toString();
        this.paperId = paperId;
        this.reviewContent = reviewContent;
        this.reviewerIdentifiedName = reviewerIdentifiedName;
        this.reviewerDisplayName = reviewerDisplayName;
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

    public String getReviewerDisplayName() {
        return reviewerDisplayName;
    }

    public void setReviewerDisplayName(String reviewerDisplayName) {
        this.reviewerDisplayName = reviewerDisplayName;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", reviewerIdentifiedName='" + reviewerIdentifiedName + '\'' +
                ", reviewerDisplayName='" + reviewerDisplayName + '\'' +
                ", paperId='" + paperId + '\'' +
                '}';
    }

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }
}
