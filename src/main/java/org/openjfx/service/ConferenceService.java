package org.openjfx.service;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.interfaces.Chair;

import java.io.IOException;
import java.util.List;

public interface ConferenceService extends DatabaseController {
    void createConference(Chair chair, Conference newConference) throws IOException;
    List<Conference> searchAllConference() throws IOException;
    Conference searchConference(String conferenceName) throws IOException;
    List<Conference> searchUsersConference(Chair chair) throws IOException;
    List<Conference> searchAvailableConference() throws IOException;

    static ConferenceService getDefaultInstance(){
        return ConferenceServiceImpl.getInstance();
    }
}
