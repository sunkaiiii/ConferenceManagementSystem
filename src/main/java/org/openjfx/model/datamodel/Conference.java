package org.openjfx.model.datamodel;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.interfaces.CSVConvertable;
import org.openjfx.service.ConferenceService;
import org.openjfx.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Conference implements CSVConvertable<Conference> {
  private String name;
  private String title;
  private String topic;
  private List<String> keywords;
  private String deadline;
  private String chairName;

    public Conference(String name, String title, String topic, List<String> keywords, String deadline) {
        this.name = name;
        this.title = title;
        this.topic = topic;
        this.keywords = keywords;
        this.deadline = deadline;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", keywords=" + keywords +
                ", deadline=" + deadline +
                '}';
    }


    @Override
    public String convertToCSVLine() {
        return CSVConvertHelper.convertClassToCSVStringLine(this);
    }

    public static void main(String[] args) throws IOException {
        Conference conference = new Conference("Cloud computing","Cloud computing 2020","Cloud computing",List.of("Cloud","Big data"),LocalDateTime.now().plusDays(4).toString());
        RegisterdUser chair = UserService.getDefaultInstance().searchAUser("123@qq.com");
        ConferenceService.getDefaultInstance().createConference(chair,conference);
    }
}
