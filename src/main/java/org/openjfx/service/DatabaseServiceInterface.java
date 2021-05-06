package org.openjfx.service;

import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.io.IOException;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface DatabaseServiceInterface {
    void addNewRecord(DatabaseName databaseName, String line) throws IOException;
    void addNewRecord(DatabaseName databaseName, CSVConvertable<?> convertableObject) throws IOException;
    <T> List<T> searchRecords(DatabaseName databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;
    <T> T searchARecord(DatabaseName databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;
    interface DatabaseName{
        String getDatabaseName();
    }
}
