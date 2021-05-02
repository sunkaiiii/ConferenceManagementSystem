package org.openjfx.service;

import com.google.gson.Gson;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.model.datamodel.factory.DataModelFactory;

import java.io.IOException;

public final class UserService {


    private static final UserService Instance = new UserService();
    private final Gson gson = new Gson();
    private final DatabaseService databaseService = DatabaseService.getInstance();

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

    private boolean findUserRecordFromLine(String[] usernames,RegisterdUser dataBaseUser){
        return dataBaseUser.getUserName().equalsIgnoreCase(usernames[0]);
    }

    private boolean checkUserCredential(String[] credential, RegisterdUser dataBaseUser){
        String userName = credential[0];
        String password = credential[1];
        return dataBaseUser.getUserName().equalsIgnoreCase(userName) && dataBaseUser.getPassword().equals(password);
    }

}
