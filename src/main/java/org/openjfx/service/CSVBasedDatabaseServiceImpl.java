package org.openjfx.service;

import org.openjfx.model.interfaces.CSVConvertable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    public <T> void deleteRecord(DatabaseController databaseName, String[] searchInfo, BiPredicate<String[], T> predicate, Function<String, T> function) throws IOException {
        File tempFile = new File(UUID.randomUUID().toString());
        File databaseFile = new File(databaseName.getDatabaseName());
        try (FileInputStream inputStream = new FileInputStream(databaseName.getDatabaseName()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                T data = function.apply(line);
                if (predicate.test(searchInfo, data)) {
                    continue;
                }
                writer.write(line + "\n");
            }
        }
        boolean success=databaseFile.delete();
        success &= tempFile.renameTo(databaseFile);
        if (!success) {
            throw new IOException("Cannot delete record");
        }
    }

    @Override
    public <T extends CSVConvertable<?>> void alterRecord(DatabaseController databaseName, String[] searchInfo, T newRecord, BiPredicate<String[], T> predicate, Function<String, T> function) throws IOException {
        File tempFile = new File(UUID.randomUUID().toString());
        File databaseFile = new File(databaseName.getDatabaseName());
        try (FileInputStream inputStream = new FileInputStream(databaseName.getDatabaseName()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                T data = function.apply(line);
                if (predicate.test(searchInfo, data)) {
                    String newLine = newRecord.convertToCSVLine();
                    writer.write(newLine + "\n");
                } else {
                    writer.write(line + "\n");
                }

            }
        }
        boolean success=databaseFile.delete();
        success &= tempFile.renameTo(databaseFile);
        if (!success) {
            throw new IOException("Cannot delete record");
        }
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
