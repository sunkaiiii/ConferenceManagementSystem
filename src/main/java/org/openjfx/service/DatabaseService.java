package org.openjfx.service;

import org.openjfx.model.interfaces.CSVConvertable;

import java.io.IOException;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface DatabaseService {
    void addNewRecord(DatabaseController databaseName, String line) throws IOException;
    void addNewRecord(DatabaseController databaseName, CSVConvertable<?> convertableObject) throws IOException;
    <T> List<T> searchRecords(DatabaseController databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;
    <T> T searchARecord(DatabaseController databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;

    static DatabaseService getDefaultInstance(){
        return CSVBasedDatabaseServiceImpl.getInstance();
    }
}
