package com.example.justpoteito.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String surname;
    private String userName;
    private String email;
    private String password;
    private int idRol;

    public User() {

    }

    public User(int id, String name, String surname, String userName, String email, String password, int idRol) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.idRol = idRol;
    }

    public User(String name, String surname, String userName, String email) {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", idRol=" + idRol +
                '}';
    }
}