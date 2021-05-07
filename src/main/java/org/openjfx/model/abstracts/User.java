package org.openjfx.model.abstracts;

public abstract class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName.toLowerCase();
        this.password = password;
    }

    public abstract boolean isAdmin();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
