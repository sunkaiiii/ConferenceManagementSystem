package org.openjfx.service;

import org.openjfx.model.interfaces.CSVConvertable;

import java.io.IOException;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface DatabaseService {
    /**
     * add a new record to database
     * @param databaseName Instance of a database controller implemented
     * @param line a converted data line
     * @throws IOException Database read failure
     */
    void addNewRecord(DatabaseController databaseName, String line) throws IOException;

    /**
     * add a new record to database
     * @param databaseName Instance of a database controller implemented
     * @param convertableObject a convertable object that can be used to serialse the data
     * @throws IOException Database read failure
     */
    void addNewRecord(DatabaseController databaseName, CSVConvertable<?> convertableObject) throws IOException;

    /**
     * delete a given data
     * @param databaseName Instance of a database controller implemented
     * @param searchInfo Search string arrays
     * @param predicate A  function to determine whether a given instance satisfies the search criteria
     * @param function A conversion function that allows a line of csv strings to be converted to the corresponding model
     * @param <T> model class
     * @throws IOException Database read failure
     */
    <T> void deleteRecord(DatabaseController databaseName, String[] searchInfo, BiPredicate<String[], T> predicate,Function<String, T> function) throws IOException;

    /**
     * Modifying data in the database
     * @param databaseName Instance of a database controller implemented
     * @param searchInfo Search string arrays
     * @param newRecord the new data that will be updated
     * @param predicate A  function to determine whether a given instance satisfies the search criteria
     * @param function A conversion function that allows a line of csv strings to be converted to the corresponding model
     * @param <T> model class
     * @throws IOException Database read failure
     */
    <T extends CSVConvertable<?>> void alterRecord(DatabaseController databaseName, String[] searchInfo,T newRecord, BiPredicate<String[], T> predicate,Function<String, T> function) throws IOException;

    /**
     * Search for multiple results
     * @param databaseName Instance of a database controller implemented
     * @param searchInfo Search string arrays
     * @param predicate A  function to determine whether a given instance satisfies the search criteria
     * @param function A conversion function that allows a line of csv strings to be converted to the corresponding model
     * @param <T> Model class
     * @return search result of the predicate
     * @throws IOException  Database read failure
     */
    <T> List<T> searchRecords(DatabaseController databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;

    /**
     * Search one results
     * @param databaseName Instance of a database controller implemented
     * @param searchInfo Search string arrays
     * @param predicate A  function to determine whether a given instance satisfies the search criteria
     * @param function A conversion function that allows a line of csv strings to be converted to the corresponding model
     * @param <T> Model class
     * @return search result of the predicate
     * @throws IOException Database read failure
     */
    <T> T searchARecord(DatabaseController databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException;

    static DatabaseService getDefaultInstance() {
        return CSVBasedDatabaseServiceImpl.getInstance();
    }
}
