package org.openjfx.model;

public class ReviewerInformation {
    private String reviewerIdentifiedName;
    private String reviewId;

    public ReviewerInformation() {
        this.reviewId = "";
    }

    public String getReviewerIdentifiedName() {
        return reviewerIdentifiedName;
    }

    public void setReviewerIdentifiedName(String reviewerIdentifiedName) {
        this.reviewerIdentifiedName = reviewerIdentifiedName;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
