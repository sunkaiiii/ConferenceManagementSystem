package org.openjfx.model.datamodel;

import org.openjfx.model.datamodel.abstracts.User;

public class Admin extends User {
    @Override
    public boolean isAdmin() {
        return true;
    }
}
