package org.openjfx.model.datamodel.factory;

import com.google.gson.Gson;
import org.openjfx.model.datamodel.Conference;
import org.openjfx.model.datamodel.Paper;
import org.openjfx.model.datamodel.RegisterdUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

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
}
