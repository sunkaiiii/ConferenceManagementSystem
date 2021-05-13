package org.openjfx.model.factory;

import com.google.gson.Gson;
import org.openjfx.model.Conference;
import org.openjfx.model.Paper;
import org.openjfx.model.RegisterdUser;
import org.openjfx.model.Review;

public class DataModelFactory {
    public static RegisterdUser convertUserFromCSVLine(String csvData) {
        return new Gson().fromJson(csvData,RegisterdUser.class);
    }

    public static Conference convertConferenceFromCSVLine(String csvData) {
        return new Gson().fromJson(csvData,Conference.class);
    }

    public static Paper convertPaperFromCSVLine(String csvData){
        return new Gson().fromJson(csvData,Paper.class);
    }

    public static Review convertReviewFromCSVLine(String csvData){
        return new Gson().fromJson(csvData, Review.class);
    }
}
