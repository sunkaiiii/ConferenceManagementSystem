package org.openjfx.model;

import org.openjfx.model.abstracts.User;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
