package org.openjfx.service;

import org.openjfx.model.datamodel.interfaces.CSVConvertable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.BiPredicate;
import java.util.function.Function;

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

    public <T> T searchARecord(String databaseName,String[] searchInfo
            ,BiPredicate<String[],String> predicate
            , Function<String,T> function) throws IOException {
        FileInputStream inputStream = null;
        BufferedReader bufferedReader=null;
        try{
            inputStream = new FileInputStream(databaseName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null){
                if(predicate.test(searchInfo,line)){
                    return function.apply(line);
                }
            }
        }catch (FileNotFoundException ignored){

        }
            finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return null;
    }
}
