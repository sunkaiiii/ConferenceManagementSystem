package org.openjfx.controllers.pagemodel;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.interfaces.Chair;
import org.openjfx.service.ConferenceService;

public class ConferenceController {
    private final ConferenceService conferenceService;

    public ConferenceController(){
        conferenceService = ConferenceService.getInstance();
    }

}
