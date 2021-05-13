package org.openjfx.service;

import org.openjfx.model.Review;

import java.io.IOException;

public interface ReviewService extends DatabaseController {
    void addReview(Review newReview) throws IOException;
}
