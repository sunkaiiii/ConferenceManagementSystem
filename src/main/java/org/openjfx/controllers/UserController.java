package org.openjfx.controllers;

import org.openjfx.model.datamodel.RegisterdUser;
import org.openjfx.service.UserService;

import java.io.IOException;

public class UserController {

    private UserService userService;

    public UserController(){
        userService = UserService.getInstance();
    }


    public void createANewUser(RegisterdUser newUser) throws CreateUserException,IOException{
        if(userService.searchAUser(newUser.getUserName())!=null){
            throw new CreateUserException();
        }
        userService.addANewUser(newUser);
    }


    public static class CreateUserException extends Exception{

    }

    public static void main(String[] args) {
        RegisterdUser user = new RegisterdUser("kai@k.com","password","Kai","Sun","44","AI","details");
        UserController controller = new UserController();
        try {
            controller.createANewUser(user);
        } catch (CreateUserException | IOException e) {
            e.printStackTrace();
        }
    }
}