package org.openjfx.service;

import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public final class DatabaseService {
    private static final DatabaseService Instance = new DatabaseService();

    public static DatabaseService getInstance() {
        return Instance;
    }

    private DatabaseService() {
    }

    public void addNewRecord(String databaseName, String line) throws IOException {
        Files.write(Paths.get(databaseName), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public void addNewRecord(String databaseName, CSVConvertable<?> convertableObject) throws IOException {
        Files.write(Paths.get(databaseName), ("\n" + convertableObject.convertToCSVLine()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public <T> List<T> searchRecords(String databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException {
        List<T> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(databaseName); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.isBlank()){
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

    public <T> T searchARecord(String databaseName, String[] searchInfo
            , BiPredicate<String[], T> predicate
            , Function<String, T> function) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(databaseName); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.isBlank()){
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
