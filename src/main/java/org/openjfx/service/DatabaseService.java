package org.openjfx.service;

import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class DatabaseService {
    private static final DatabaseService Instance = new DatabaseService();

    public static DatabaseService getInstance() {
        return Instance;
    }

    private DatabaseService(){

    }

    public void addNewRecord(String databaseName, String line) throws IOException {
        Files.write(Paths.get(databaseName),line.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }

    public void addNewRecord(String databaseName, CSVConvertable<?> convertableObject) throws IOException {
        Files.write(Paths.get(databaseName),convertableObject.convertToCSVLine().getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }
}
