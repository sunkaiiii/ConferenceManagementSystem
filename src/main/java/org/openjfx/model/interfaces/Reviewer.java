package org.openjfx.model.interfaces;

import java.util.Map;

public interface Reviewer {
    String getReviewerName();
    String getReviewerIdentifiedName();
    Map<String,Integer> getInterestAreas();
}
