package org.openjfx.service;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.interfaces.Chair;

import java.io.IOException;

public class ConferenceService {
    private static final ConferenceService Instance = new ConferenceService();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "conference_table.csv";

    private final DatabaseService databaseService = DatabaseService.getInstance();

    public static ConferenceService getInstance() {
        return Instance;
    }

    public void createConference(Chair chair,Conference newConference) throws IOException {
        newConference.setChair(chair);
        databaseService.addNewRecord(CONFERENCE_DATABASE_FILE_NAME,newConference);
    }

    public Conference searchConference(String conferenceName){
        return null;
//        return databaseService.searchARecord(CONFERENCE_DATABASE_FILE_NAME,new String[]{conferenceName},this::searchConference,)
    }

    private boolean searchConference(String[] conferenceName,String databaseRecord){
        return false;
    }
}
