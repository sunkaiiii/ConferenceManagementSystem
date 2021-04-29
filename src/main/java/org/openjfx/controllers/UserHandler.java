package org.openjfx.controllers;

import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.service.UserService;

import java.io.IOException;

public class UserHandler {

    private UserService userService;

    public UserHandler() {
        userService = UserService.getInstance();
    }


    public void createANewUser(RegisterdUser newUser) throws CreateUserException, IOException {
        if (userService.searchAUser(newUser.getUserName()) != null) {
            throw new CreateUserException();
        }
        userService.addANewUser(newUser);
    }

    public RegisterdUser checkUserCredential(String userName, String password) throws IOException {
        return userService.checkUserCredential(userName, password);
    }


    public static class CreateUserException extends Exception {

    }

    public static void main(String[] args) {
        RegisterdUser user = new RegisterdUser("kai@k.com", "password", "Kai", "Sun", "44", "AI", "details");
        UserHandler controller = new UserHandler();
        try {
            controller.createANewUser(user);
        } catch (CreateUserException | IOException e) {
            e.printStackTrace();
        }
    }
}
