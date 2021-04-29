package org.openjfx.service;

import com.google.gson.Gson;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.model.datamodel.factory.DataModelFactory;

import java.io.IOException;

public final class UserService {


    private static UserService Instance = new UserService();
    private final Gson gson = new Gson();
    private DatabaseService databaseService = DatabaseService.getInstance();

    private final String userDatabaseFileName = "user_table.csv";

    private UserService(){

    }

    public static UserService getInstance() {
        return Instance;
    }

    public void addANewUser(RegisterdUser newUser) throws IOException {
        databaseService.addNewRecord(this.userDatabaseFileName,newUser);
    }

    public RegisterdUser searchAUser(String userName) throws IOException {
        return databaseService.searchARecord(userDatabaseFileName,new String[]{userName},this::findUserRecordFromLine,DataModelFactory::convertUserFromCSVLine);
    }

    public RegisterdUser checkUserCredential(String userName, String password) throws IOException {
        return databaseService.searchARecord(userDatabaseFileName,new String[]{userName,password},this::checkUserCredential, DataModelFactory::convertUserFromCSVLine);
    }

    private boolean findUserRecordFromLine(String[] usernames,String data){
        if(data.isBlank()){
            return false;
        }
        RegisterdUser dataBaseUser = gson.fromJson(data,RegisterdUser.class);
        return dataBaseUser.getUserName().equalsIgnoreCase(usernames[0]);
    }

    private boolean checkUserCredential(String[] credential, String data){
        if(data.isBlank()){
            return false;
        }
        RegisterdUser dataBaseUser = gson.fromJson(data,RegisterdUser.class);
        String userName = credential[0];
        String password = credential[1];
        return dataBaseUser.getUserName().equalsIgnoreCase(userName) && dataBaseUser.getPassword().equals(password);
    }

}
