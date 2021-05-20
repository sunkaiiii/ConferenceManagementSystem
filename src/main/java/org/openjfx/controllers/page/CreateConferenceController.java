package org.openjfx.controllers.page;

import org.openjfx.MainApp;
import org.openjfx.controllers.page.abspage.AbsConferenceViewController;
import org.openjfx.model.Conference;
import org.openjfx.model.interfaces.Chair;

import java.io.IOException;

public class CreateConferenceController extends AbsConferenceViewController {

    @Override
    protected void setViewState() {

    }

    @Override
    protected void handleConference(Conference conference) throws IOException {
        saveConferenceDataToDatabase(conference);
    }

    private void saveConferenceDataToDatabase(Conference newConference) throws IOException {
        Chair chair = MainApp.getInstance().getUser();
        conferenceService.createConference(chair, newConference);
    }
}
