package org.openjfx.service;

import org.openjfx.helper.CSVConvertHelper;
import org.openjfx.model.datamodel.AuthorInformation;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.factory.DataModelFactory;
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
        paper.setAuthors(authors.stream().map(author -> new AuthorInformation(author.getAuthorName(),author.getAuthorIdentifiedName())).collect(Collectors.toList()));
        databaseService.addNewRecord(PAPER_DATABASE_FILE_NAME, paper);
    }

    public List<Paper> getUserPapers(Author author) throws IOException{
        return databaseService.searchRecords(PAPER_DATABASE_FILE_NAME,new String[]{author.getAuthorIdentifiedName()},this::findMyPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    private boolean findMyPaper(String[] authorName, Paper paper){
        String identifyName = authorName[0];
        return paper.getAuthors().stream().anyMatch(information->information.getAuthorIdentifyName().equalsIgnoreCase(identifyName));
    }
}
