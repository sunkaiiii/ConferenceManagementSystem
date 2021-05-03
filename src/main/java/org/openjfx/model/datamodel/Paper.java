package org.openjfx.model.datamodel;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.util.List;

public class Paper implements CSVConvertable<Conference> {

    private String title;
    private String topic;
    private List<String> keywords;
    private String deadline;
    private List<PaperFile> paperFiles;
    private String conferenceName;
    private List<String> authors;

    public Paper(String title, String topic, List<String> keywords, String deadline,List<PaperFile> paperFiles, String conferenceName) {
        this.title = title;
        this.topic = topic;
        this.keywords = keywords;
        this.deadline = deadline;
        this.paperFiles = paperFiles;
        this.conferenceName = conferenceName;
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

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", keywords=" + keywords +
                ", deadline='" + deadline + '\'' +
                ", paperFiles=" + paperFiles +
                ", conferenceName='" + conferenceName + '\'' +
                ", authors=" + authors +
                '}';
    }

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }
}
