package org.openjfx.service;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.factory.DataModelFactory;
import org.openjfx.model.datamodel.interfaces.Chair;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ConferenceService {
    private static final ConferenceService Instance = new ConferenceService();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "conference_table.csv";

    private final DatabaseService databaseService = DatabaseService.getInstance();

    public static ConferenceService getInstance() {
        return Instance;
    }

    private ConferenceService() {

    }

    public void createConference(Chair chairName, Conference newConference) throws IOException {
        newConference.setChairName(chairName.getChairName());
        databaseService.addNewRecord(CONFERENCE_DATABASE_FILE_NAME, newConference);
    }

    public List<Conference> searchAllConference() throws IOException {
        return databaseService.searchRecords(CONFERENCE_DATABASE_FILE_NAME, null, (predicate, data) -> true, DataModelFactory::convertConferenceFromCSVLine);
    }

    public Conference searchConference(String conferenceName) {
        return null;
//        return databaseService.searchARecord(CONFERENCE_DATABASE_FILE_NAME,new String[]{conferenceName},this::searchConference,)
    }

    public List<Conference> searchUsersConference(Chair chair) throws IOException {
        return databaseService.searchRecords(CONFERENCE_DATABASE_FILE_NAME, new String[]{chair.getChairName()}, this::searchConferenceBelongToUser, DataModelFactory::convertConferenceFromCSVLine);
    }

    public List<Conference> searchAvailableConference() throws IOException {
        return databaseService.searchRecords(CONFERENCE_DATABASE_FILE_NAME,null,this::judgeIsAvailableConference,DataModelFactory::convertConferenceFromCSVLine);
    }

    private boolean searchConference(String[] conferenceName, String databaseRecord) {
        return false;
    }

    private boolean searchConferenceBelongToUser(String[] usernames, Conference conference) {
        return Arrays.binarySearch(usernames, conference.getChairName()) >= 0;
    }

    private boolean judgeIsAvailableConference(String[] searchInfo, Conference conference) {
        return Duration.between(LocalDateTime.now(), LocalDateTime.parse(conference.getDeadline())).toDays() > 1;
    }
}
