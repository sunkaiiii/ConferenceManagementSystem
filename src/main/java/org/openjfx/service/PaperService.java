package org.openjfx.service;

import org.openjfx.model.datamodel.interfaces.Author;

import java.util.List;

public class PaperService {
    private static final PaperService Instance = new PaperService();
    private static final String CONFERENCE_DATABASE_FILE_NAME = "paper_table.csv";

    private final DatabaseService databaseService = DatabaseService.getInstance();

    public static PaperService getInstance() {
        return Instance;
    }

    private PaperService() {

    }
}
