package org.openjfx.service;

import org.openjfx.model.datamodel.Admin;
import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.model.datamodel.abstracts.User;
import org.openjfx.model.datamodel.interfaces.Author;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void addANewUser(RegisterdUser newUser) throws IOException;
    RegisterdUser searchAUser(String userName) throws IOException;
    RegisterdUser checkUserCredential(String userName, String password) throws IOException;
    List<Author> findAuthors() throws IOException;
    Admin adminLogin(String userName, String password);
    List<RegisterdUser> findAllUser(User user) throws IOException;

    static UserService getDefaultInstance(){
        return new UserServiceImpl();
    }
}
