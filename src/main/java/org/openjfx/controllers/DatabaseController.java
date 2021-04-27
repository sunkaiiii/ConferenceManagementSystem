package org.openjfx.controllers;

import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.service.UserService;

import java.io.IOException;

public class DatabaseController {

    private UserService userService;

    public DatabaseController(){
        userService = UserService.getInstance();
    }

    public RegisterdUser searchAUser(String email){
        return null;
    }

    public void createANewUser(RegisterdUser newUser) throws CreateUserException,IOException{
        if(userService.searchAUser(newUser.getUserName())!=null){
            throw new CreateUserException();
        }
        userService.addANewUser(newUser);
    }


    public static class CreateUserException extends Exception{

    }
}
