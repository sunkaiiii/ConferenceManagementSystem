package org.openjfx.service;

import org.openjfx.model.Review;

import java.io.IOException;

public class ReviewServiceImpl implements ReviewService{
    private static final ReviewServiceImpl Instance = new ReviewServiceImpl();
    private static final String REVIEW_DATABASE_FILE_NAME = "review_table.csv";
    private final DatabaseService databaseService = DatabaseService.getDefaultInstance();

    private ReviewServiceImpl(){}
    @Override
    public String getDatabaseName() {
        return REVIEW_DATABASE_FILE_NAME;
    }

    @Override
    public void addReview(Review newReview) throws IOException {
        databaseService.addNewRecord(this,newReview);
    }
}
