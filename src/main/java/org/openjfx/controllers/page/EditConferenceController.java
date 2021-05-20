package org.openjfx.controllers.page;

import org.openjfx.controllers.page.abspage.AbsConferenceViewController;
import org.openjfx.model.Conference;

import java.io.IOException;

public class EditConferenceController extends AbsConferenceViewController {
    @Override
    protected void setViewState() {

    }

    @Override
    protected void handleConference(Conference conference) throws IOException {
        conferenceService.updateConference(conference);
    }
}
