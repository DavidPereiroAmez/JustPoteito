package com.example.justpoteito.models;

public class UserResponse {

    private boolean access;
    private String message;
    private String email;
    private int id;

    public UserResponse() {
    }

    public UserResponse(boolean access, String message) {
        this.access = access;
        this.message = message;
    }

    public UserResponse(boolean hasAccess, String message, String username, int id) {
        this.access = hasAccess;
        this.message = message;
        this.email = username;
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "hasAccess=" + access +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
