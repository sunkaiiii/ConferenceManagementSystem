package org.openjfx.service;

import javafx.util.Pair;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.model.factory.DataModelFactory;
import org.openjfx.model.interfaces.Author;
import org.openjfx.model.interfaces.Reviewer;

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
        paper.setAuthors(authors.stream().map(author -> new AuthorInformation(author.getAuthorName(), author.getAuthorIdentifiedName())).collect(Collectors.toList()));
        databaseService.addNewRecord(this, paper);
    }

    @Override
    public void setReviewer(Paper paper, List<Reviewer> reviewers) throws IOException {
        paper.setReviewers(reviewers.stream().map(this::createReviewerPair).collect(Collectors.toList()));
        paper.setPaperStatus(Paper.PaperStatus.BEING_REVIEWED);
        databaseService.alterRecord(this, new String[]{paper.getTitle(), paper.getConferenceName()}, paper, this::findPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    private Pair<String,String> createReviewerPair(Reviewer reviewer){
        return new Pair<>(reviewer.getReviewerIdentifiedName(),"");
    }

    private boolean findPaper(String[] searchInfo, Paper paper) {
        String title = searchInfo[0];
        String conferenceName = searchInfo[1];
        return paper.getConferenceName().equals(conferenceName) && paper.getTitle().equals(title);
    }

    @Override
    public List<Paper> getUserPapers(Author author) throws IOException {
        return databaseService.searchRecords(this, new String[]{author.getAuthorIdentifiedName()}, this::findMyPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public List<Paper> getConferencePaper(Conference conference) throws IOException {
        return databaseService.searchRecords(this, new String[]{conference.getName()}, this::findConferencePaper, DataModelFactory::convertPaperFromCSVLine);
    }

    private boolean findMyPaper(String[] authorName, Paper paper) {
        String identifyName = authorName[0];
        return paper.getAuthors().stream().anyMatch(information -> information.getAuthorIdentifyName().equalsIgnoreCase(identifyName));
    }

    private boolean findConferencePaper(String[] conferenceNames, Paper paper) {
        String conferenceName = conferenceNames[0];
        return paper.getConferenceName().equalsIgnoreCase(conferenceName);
    }

    @Override
    public String getDatabaseName() {
        return PAPER_DATABASE_FILE_NAME;
    }
}
