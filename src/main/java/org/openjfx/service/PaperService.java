package org.openjfx.service;

import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.interfaces.Author;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PaperService {
    private static final PaperService Instance = new PaperService();
    private static final String PAPER_DATABASE_FILE_NAME = "paper_table.csv";

    private final DatabaseService databaseService = DatabaseService.getInstance();

    public static PaperService getInstance() {
        return Instance;
    }

    private PaperService() {

    }

    public void submitPaper(List<Author> authors, Paper paper) throws IOException {
        paper.setAuthors(authors.stream().map(Author::getAuthorIdentifiedName).collect(Collectors.toList()));
        databaseService.addNewRecord(PAPER_DATABASE_FILE_NAME, paper);
    }
}
