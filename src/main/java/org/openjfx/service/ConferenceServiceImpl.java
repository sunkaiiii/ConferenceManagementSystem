package org.openjfx.service;

import org.openjfx.model.Conference;
import org.openjfx.model.factory.DataModelFactory;
import org.openjfx.model.interfaces.Chair;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

final class ConferenceServiceImpl implements ConferenceService {
    private static final ConferenceServiceImpl Instance = new ConferenceServiceImpl();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "conference_table.csv";

    private final DatabaseService databaseService = DatabaseService.getDefaultInstance();

    static ConferenceServiceImpl getInstance() {
        return Instance;
    }

    private ConferenceServiceImpl() {

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
    public Conference searchConferenceById(String id) throws IOException {
        return databaseService.searchARecord(this, new String[]{id}, this::searchConferenceById, DataModelFactory::convertConferenceFromCSVLine);
    }

    @Override
    public String getConferenceNameById(String id) {
        try {
            Conference conference = searchConferenceById(id);
            return conference.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Conference searchConferenceByName(String conferenceName) throws IOException {
        return databaseService.searchARecord(this, new String[]{conferenceName}, this::searchConferenceByName, DataModelFactory::convertConferenceFromCSVLine);
    }

    @Override
    public List<Conference> searchUsersConference(Chair chair) throws IOException {
        return databaseService.searchRecords(this, new String[]{chair.getChairName()}, this::searchConferenceBelongToUser, DataModelFactory::convertConferenceFromCSVLine);
    }

    @Override
    public List<Conference> searchAvailableConference() throws IOException {
        return databaseService.searchRecords(this, null, this::judgeIsAvailableConference, DataModelFactory::convertConferenceFromCSVLine);
    }

    private boolean searchConferenceById(String[] id, Conference conference) {
        return conference.getId().equals(id[0]);
    }

    private boolean searchConferenceByName(String[] conferenceName, Conference conference) {
        return conference.getName().equalsIgnoreCase(conferenceName[0]);
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
