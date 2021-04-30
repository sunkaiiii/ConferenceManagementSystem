package org.openjfx.service;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.factory.DataModelFactory;

import java.io.IOException;
import java.util.List;

public class ConferenceService {
    private static final ConferenceService Instance = new ConferenceService();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "conference_table.csv";

    private final DatabaseService databaseService = DatabaseService.getInstance();

    public static ConferenceService getInstance() {
        return Instance;
    }

    public void createConference(String chairName, Conference newConference) throws IOException {
        newConference.setChairName(chairName);
        databaseService.addNewRecord(CONFERENCE_DATABASE_FILE_NAME,newConference);
    }

    public List<Conference> searchAllConference() throws IOException {
        return databaseService.searchRecords(CONFERENCE_DATABASE_FILE_NAME,null,(predicate,data)->true, DataModelFactory::convertConferenceFromCSVLine);
    }
    public Conference searchConference(String conferenceName){
        return null;
//        return databaseService.searchARecord(CONFERENCE_DATABASE_FILE_NAME,new String[]{conferenceName},this::searchConference,)
    }

    private boolean searchConference(String[] conferenceName,String databaseRecord){
        return false;
    }
}
