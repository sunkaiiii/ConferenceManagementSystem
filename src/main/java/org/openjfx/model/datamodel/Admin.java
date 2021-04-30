package org.openjfx.model.datamodel;

import org.openjfx.model.datamodel.abstracts.User;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
