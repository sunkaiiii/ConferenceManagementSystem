package org.openjfx.service;

import javafx.util.Pair;
import org.openjfx.model.AuthorInformation;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.model.ReviewerInformation;
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
        paper.setReviewerInformationList(reviewers.stream().map(this::createReviewerPair).collect(Collectors.toList()));
        paper.setPaperStatus(Paper.PaperStatus.BEING_REVIEWED);
        databaseService.alterRecord(this, new String[]{paper.getId()}, paper, this::findPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public void updatePaperStatus(Paper updatedPaper) throws IOException {
        databaseService.alterRecord(this,new String[]{updatedPaper.getId()},updatedPaper,this::findPaper,DataModelFactory::convertPaperFromCSVLine);
    }

    private ReviewerInformation createReviewerPair(Reviewer reviewer) {
        ReviewerInformation information = new ReviewerInformation();
        information.setReviewerIdentifiedName(reviewer.getReviewerIdentifiedName());
        return information;
    }

    private boolean findPaper(String[] searchInfo, Paper paper) {
        String paperId = searchInfo[0];
        return paper.getId().equals(paperId);
    }

    @Override
    public List<Paper> getUserPapers(Author author) throws IOException {
        return databaseService.searchRecords(this, new String[]{author.getAuthorIdentifiedName()}, this::findMyPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public List<Paper> getConferencePaper(Conference conference) throws IOException {
        return databaseService.searchRecords(this, new String[]{conference.getName()}, this::findConferencePaper, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public List<Paper> findPaperNeedToBeReviewedByTheUser(Reviewer reviewer) throws IOException {
        return databaseService.searchRecords(this, new String[]{reviewer.getReviewerIdentifiedName()}, this::checkThePaperNeedsToBeReviewedByTheUser, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public List<Paper> findPaperReviewedByTheUser(Reviewer reviewer) throws IOException {
        return databaseService.searchRecords(this, new String[]{reviewer.getReviewerIdentifiedName()}, this::checkThePaperIsReviewedByTheUser, DataModelFactory::convertPaperFromCSVLine);
    }

    @Override
    public void setReviewRecordToPaper(String paperId, String reviewerIdentifiedName, String id) throws IOException {
        Paper paper = databaseService.searchARecord(this, new String[]{paperId}, this::findPaper, DataModelFactory::convertPaperFromCSVLine);
        var reviewerInformation = paper.getReviewerInformationList().stream().filter(information -> information.getReviewerIdentifiedName().equals(reviewerIdentifiedName)).findFirst();
        if (reviewerInformation.isPresent()) {
            ReviewerInformation information = reviewerInformation.get();
            information.setReviewId(id);
        }
        if (paper.getReviewerInformationList().stream().noneMatch(information -> information.getReviewId().isBlank())) {
            paper.setPaperStatus(Paper.PaperStatus.REVIEWED);
        }
        databaseService.alterRecord(this, new String[]{paper.getId()}, paper, this::findPaper, DataModelFactory::convertPaperFromCSVLine);
    }

    private boolean checkThePaperNeedsToBeReviewedByTheUser(String[] identifiedName, Paper paper) {
        List<ReviewerInformation> reviewerInformationList = paper.getReviewerInformationList();
        if (reviewerInformationList == null) {
            return false;
        }
        for (final var information : reviewerInformationList) {
            if (information.getReviewerIdentifiedName().equals(identifiedName[0]) && information.getReviewId().isBlank()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkThePaperIsReviewedByTheUser(String[] searchInfo, Paper paper) {
        List<ReviewerInformation> reviewerInformationList = paper.getReviewerInformationList();
        if (reviewerInformationList == null) {
            return false;
        }
        for (final ReviewerInformation reviewerInformation : reviewerInformationList) {
            if (reviewerInformation.getReviewerIdentifiedName().equals(searchInfo[0]) && !reviewerInformation.getReviewId().isBlank()) {
                return true;
            }
        }
        return false;
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
