package org.openjfx.service;

import org.openjfx.model.Review;
import org.openjfx.model.factory.DataModelFactory;

import java.io.IOException;

public class ReviewServiceImpl implements ReviewService {
    private static final ReviewServiceImpl Instance = new ReviewServiceImpl();
    private static final String REVIEW_DATABASE_FILE_NAME = "review_table.csv";
    private final DatabaseService databaseService = DatabaseService.getDefaultInstance();
    private final PaperService paperService = PaperService.getDefaultInstance();

    private ReviewServiceImpl() {
    }

    @Override
    public String getDatabaseName() {
        return REVIEW_DATABASE_FILE_NAME;
    }

    @Override
    public void addReview(Review newReview) throws IOException {
        databaseService.addNewRecord(this, newReview);
        paperService.setReviewRecordToPaper(newReview.getPaperId(), newReview.getReviewerIdentifiedName(), newReview.getId());
    }

    @Override
    public Review searchReviewById(String id) throws IOException {
        return databaseService.searchARecord(this, new String[]{id}, (idArray, review) -> idArray[0].equals(review.getId()), DataModelFactory::convertReviewFromCSVLine);
    }

    public static ReviewService getInstance() {
        return Instance;
    }
}
