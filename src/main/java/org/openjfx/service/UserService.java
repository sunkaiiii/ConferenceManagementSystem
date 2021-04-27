package org.openjfx.service;

import org.openjfx.model.datamodel.RegisterdUser;

import java.io.IOException;

public final class UserService {


    private static UserService Instance = new UserService();
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
        return databaseService.searchARecord(userDatabaseFileName,new String[]{userName},this::findUserRecordFromLine,RegisterdUser::readFromCSVString);
    }

    private boolean findUserRecordFromLine(String[] usernames,String data){
        String userName = usernames[0];
        return data.split(",")[0].equalsIgnoreCase(userName);
    }

}
