package org.openjfx.service;

import org.openjfx.model.datamodel.AuthorInformation;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.factory.DataModelFactory;
import org.openjfx.model.datamodel.interfaces.Author;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

final class PaperServiceImpl implements PaperService {
    private static final PaperServiceImpl Instance = new PaperServiceImpl();
    private static final String PAPER_DATABASE_FILE_NAME = "paper_table.csv";

    private final DatabaseService databaseService = DatabaseService.getDefaultInstance();

    static PaperServiceImpl getInstance() {
        return Instance;
    }

    private PaperServiceImpl() {

    }

    @Override
    public void submitPaper(List<Author> authors, Paper paper) throws IOException {
        paper.setAuthors(authors.stream().map(author -> new AuthorInformation(author.getAuthorName(),author.getAuthorIdentifiedName())).collect(Collectors.toList()));
        databaseService.addNewRecord(this, paper);
    }

    @Override
    public List<Paper> getUserPapers(Author author) throws IOException{
        return databaseService.searchRecords(this,new String[]{author.getAuthorIdentifiedName()},this::findMyPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public List<Paper> getConferencePaper(Conference conference) throws IOException{
        return databaseService.searchRecords(this,new String[]{conference.getName()},this::findConferencePaper,DataModelFactory::convertPaperFromCSVLine);
    }

    private boolean findMyPaper(String[] authorName, Paper paper){
        String identifyName = authorName[0];
        return paper.getAuthors().stream().anyMatch(information->information.getAuthorIdentifyName().equalsIgnoreCase(identifyName));
    }

    private boolean findConferencePaper(String[] conferenceNames, Paper paper){
        String conferenceName = conferenceNames[0];
        return paper.getConferenceName().equalsIgnoreCase(conferenceName);
    }

    @Override
    public String getDatabaseName() {
        return PAPER_DATABASE_FILE_NAME;
    }
}
