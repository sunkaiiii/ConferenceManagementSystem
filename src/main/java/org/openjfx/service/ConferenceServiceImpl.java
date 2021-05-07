package org.openjfx.service;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.factory.DataModelFactory;
import org.openjfx.model.datamodel.interfaces.Chair;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

final class ConferenceServiceImpl implements ConferenceService {
    private static final ConferenceServiceImpl Instance = new ConferenceServiceImpl();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "conference_table.csv";

    private final DatabaseService databaseService = DatabaseService.getDefaultInstance();

    public static ConferenceServiceImpl getInstance() {
        return Instance;
    }

    ConferenceServiceImpl() {

    }

    @Override
    public void createConference(Chair chair, Conference newConference) throws IOException {
        newConference.setChairName(chair.getChairName());
        databaseService.addNewRecord(this, newConference);
    }

    @Override
    public List<Conference> searchAllConference() throws IOException {
        return databaseService.searchRecords(this, null, (predicate, data) -> true, DataModelFactory::convertConferenceFromCSVLine);
    }

    @Override
    public Conference searchConference(String conferenceName) throws IOException {
        return null;
//        return databaseService.searchARecord(CONFERENCE_DATABASE_FILE_NAME,new String[]{conferenceName},this::searchConference,)
    }

    @Override
    public List<Conference> searchUsersConference(Chair chair) throws IOException {
        return databaseService.searchRecords(this, new String[]{chair.getChairName()}, this::searchConferenceBelongToUser, DataModelFactory::convertConferenceFromCSVLine);
    }

    @Override
    public List<Conference> searchAvailableConference() throws IOException {
        return databaseService.searchRecords(this,null,this::judgeIsAvailableConference,DataModelFactory::convertConferenceFromCSVLine);
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

    @Override
    public String getDatabaseName() {
        return CONFERENCE_DATABASE_FILE_NAME;
    }
}
