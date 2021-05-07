package org.openjfx.service;

import org.openjfx.model.interfaces.CSVConvertable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public final class CSVBasedDatabaseServiceImpl implements DatabaseService {
    private static final CSVBasedDatabaseServiceImpl Instance = new CSVBasedDatabaseServiceImpl();

    static CSVBasedDatabaseServiceImpl getInstance() {
        return Instance;
    }

    private CSVBasedDatabaseServiceImpl() {
    }

    @Override
    public void addNewRecord(DatabaseController databaseController, String line) throws IOException {
        Files.write(Paths.get(databaseController.getDatabaseName()), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    @Override
    public void addNewRecord(DatabaseController databaseController, CSVConvertable<?> convertableObject) throws IOException {
        Files.write(Paths.get(databaseController.getDatabaseName()), ("\n" + convertableObject.convertToCSVLine()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    @Override
    public <T> List<T> searchRecords(DatabaseController databaseController, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException {
        List<T> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(databaseController.getDatabaseName()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                T data = function.apply(line);
                if (predicate.test(searchInfo, data)) {
                    result.add(data);
                }
            }
        } catch (FileNotFoundException ignored) {

        }
        return result;
    }

    @Override
    public <T> T searchARecord(DatabaseController databaseController, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(databaseController.getDatabaseName()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                T data = function.apply(line);
                if (predicate.test(searchInfo, data)) {
                    return data;
                }
            }
        } catch (FileNotFoundException ignored) {

        }
        return null;
    }
}
