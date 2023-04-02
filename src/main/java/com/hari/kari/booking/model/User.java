package com.hari.kari.booking.model;

public class User {
    private String userName;
    private UserType userType;
    public User(String userName, UserType userType) {
        this.userName= userName;
        this.userType= userType;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
