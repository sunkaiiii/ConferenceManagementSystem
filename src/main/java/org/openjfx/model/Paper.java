package org.openjfx.model;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.interfaces.CSVConvertable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Paper implements CSVConvertable<Conference> {
    private String id;
    private String title;
    private String topic;
    private List<String> keywords;
    private String deadline;
    private List<PaperFile> paperFiles;
    private String conferenceId;
    private String submittedTime;
    private List<AuthorInformation> authors;
    private PaperStatus paperStatus;
    private List<ReviewerInformation> reviewerInformationList;

    public Paper(String title, String topic, List<String> keywords, String deadline, List<PaperFile> paperFiles, String conferenceId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.topic = topic;
        this.keywords = keywords;
        this.deadline = deadline;
        this.paperFiles = paperFiles;
        this.submittedTime = LocalDateTime.now().toString();
        this.conferenceId = conferenceId;
        this.paperStatus = PaperStatus.SUBMITTED;
        this.reviewerInformationList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List<PaperFile> getPaperFiles() {
        return paperFiles;
    }

    public void setPaperFiles(List<PaperFile> paperFiles) {
        this.paperFiles = paperFiles;
    }


    public List<AuthorInformation> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorInformation> authors) {
        this.authors = authors;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }


    public PaperStatus getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(PaperStatus paperStatus) {
        this.paperStatus = paperStatus;
    }

    public void setSubmittedTime(String submittedTime) {
        this.submittedTime = submittedTime;
    }

    public List<ReviewerInformation> getReviewerInformationList() {
        return reviewerInformationList;
    }

    public void setReviewerInformationList(List<ReviewerInformation> reviewerInformationList) {
        this.reviewerInformationList = reviewerInformationList;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public boolean isProcessed() {
        return paperStatus == PaperStatus.ACCEPTED || paperStatus == PaperStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", keywords=" + keywords +
                ", deadline='" + deadline + '\'' +
                ", paperFiles=" + paperFiles +
                ", conferenceId='" + conferenceId + '\'' +
                ", submittedTime='" + submittedTime + '\'' +
                ", authors=" + authors +
                ", paperStatus=" + paperStatus +
                ", reviewerInformationList=" + reviewerInformationList +
                '}';
    }

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }

    public enum PaperStatus {
        SUBMITTED("Submitted"),
        ACCEPTED("Accepted"),
        REJECTED("Rejected"),
        BEING_REVIEWED("Being Reviewed"),
        REVIEWED("Reviewed");
        private String status;

        PaperStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "PaperStatus{" +
                    "status='" + status + '\'' +
                    '}';
        }
    }
}
