package com.example.justpoteito.models;

import java.io.Serializable;

public class Cook implements Serializable {
    private int id;
    private String name;
    private String lastNames;

    public Cook() {
    }

    public Cook(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Cook(int id, String name, String lastNames) {
        this.id = id;
        this.name = name;
        this.lastNames = lastNames;
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

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastNames='" + lastNames + '\'' +
                '}';
    }
}
