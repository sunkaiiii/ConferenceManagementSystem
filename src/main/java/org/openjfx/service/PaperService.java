package org.openjfx.service;

import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.interfaces.Author;

import java.io.IOException;
import java.util.List;

public interface PaperService extends DatabaseController {
    void submitPaper(List<Author> authors, Paper paper) throws IOException;
    List<Paper> getUserPapers(Author author) throws IOException;
    List<Paper> getConferencePaper(Conference conference) throws IOException;

    static PaperService getDefaultInstance(){
        return new PaperServiceImpl();
    }
}
