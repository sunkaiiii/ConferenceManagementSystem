package org.openjfx.service;

import com.google.gson.Gson;
import org.openjfx.MainApp;
import org.openjfx.model.datamodel.Admin;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.model.datamodel.abstracts.User;
import org.openjfx.model.datamodel.factory.DataModelFactory;
import org.openjfx.model.datamodel.interfaces.Author;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class UserServiceImpl implements UserService {


    private static final UserServiceImpl Instance = new UserServiceImpl();
    private final Gson gson = new Gson();
    private final DatabaseService databaseService = DatabaseService.getInstance();

    private final String ADMIN_NAME = "Admin";

    private final String USER_DATABASE_FILE_NAME = "user_table.csv";

    UserServiceImpl(){

    }


    public static UserServiceImpl getInstance() {
        return Instance;
    }

    @Override
    public void addANewUser(RegisterdUser newUser) throws IOException {
        databaseService.addNewRecord(this.USER_DATABASE_FILE_NAME,newUser);
    }

    @Override
    public RegisterdUser searchAUser(String userName) throws IOException {
        return databaseService.searchARecord(USER_DATABASE_FILE_NAME,new String[]{userName},this::findUserRecordFromLine,DataModelFactory::convertUserFromCSVLine);
    }

    @Override
    public RegisterdUser checkUserCredential(String userName, String password) throws IOException {
        return databaseService.searchARecord(USER_DATABASE_FILE_NAME,new String[]{userName,password},this::checkUserCredential, DataModelFactory::convertUserFromCSVLine);
    }

    @Override
    public List<Author> findAuthors() throws IOException{
        return new ArrayList<>(databaseService.searchRecords(USER_DATABASE_FILE_NAME, null, this::findAuthorFromKeywords, DataModelFactory::convertUserFromCSVLine));
    }

    @Override
    public Admin adminLogin(String userName, String password){
        if(userName.equalsIgnoreCase("admin") && "Admin".equals(password)){
            return new Admin(userName, password);
        }
        return null;
    }

    @Override
    public List<RegisterdUser> findAllUser(User user) throws IOException {
        if(!user.isAdmin()){
            return new ArrayList<>();
        }
        return databaseService.searchRecords(USER_DATABASE_FILE_NAME,null,(s,u)->true,DataModelFactory::convertUserFromCSVLine);
    }

    private boolean findUserRecordFromLine(String[] usernames,RegisterdUser dataBaseUser){
        return dataBaseUser.getUserName().equalsIgnoreCase(usernames[0]);
    }

    private boolean checkUserCredential(String[] credential, RegisterdUser dataBaseUser){
        String userName = credential[0];
        String password = credential[1];
        return dataBaseUser.getUserName().equalsIgnoreCase(userName) && dataBaseUser.getPassword().equals(password);
    }

    private boolean findAuthorFromKeywords(String[] keywords, RegisterdUser user){
        return !user.getUserName().equalsIgnoreCase(MainApp.getInstance().getUser().getUserName()) && !user.getUserName().equalsIgnoreCase(ADMIN_NAME);
    }

}
