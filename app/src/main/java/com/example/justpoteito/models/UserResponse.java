package com.example.justpoteito.models;

import java.util.Set;

public class UserResponse {

    private boolean access;
    private String message;
    private String userName;
    private int id;
    private String name;
    private String surnames;
    private String email;
    private String password;
    private boolean isEnabled;

    public UserResponse() {
    }

    public UserResponse(boolean access, String message) {
        this.access = access;
        this.message = message;
    }

    public UserResponse(boolean access, String message, String userName, int id, String name, String surnames, String email, String password, boolean isEnabled) {
        this.access = access;
        this.message = message;
        this.userName = userName;
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean hasAccess) {
        this.access = hasAccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "access=" + access +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
