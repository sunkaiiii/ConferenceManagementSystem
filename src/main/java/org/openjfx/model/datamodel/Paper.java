package org.openjfx.model.datamodel;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.util.List;

public class Paper implements CSVConvertable<Conference> {

    private String title;
    private String topic;
    private List<String> keywords;
    private String deadline;

    public Paper(String title, String topic, List<String> keywords, String deadline) {
        this.title = title;
        this.topic = topic;
        this.keywords = keywords;
        this.deadline = deadline;
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

    @Override
    public String toString() {
        return "Paper{" +
                "title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", keywords=" + keywords +
                ", deadline='" + deadline + '\'' +
                '}';
    }

    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }
}
