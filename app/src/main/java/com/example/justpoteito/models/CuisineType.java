package com.example.justpoteito.models;

import java.io.Serializable;
import java.util.List;

public class CuisineType implements Serializable {
    private int id;
    private String name;
    private String subtype;
    private String image;

    public CuisineType() {
    }

    public CuisineType(int id, String name, String subtype, String image) {
        this.id = id;
        this.name = name;
        this.subtype = subtype;
        this.image = image;
    }

    public CuisineType(int id, String name, String subtype) {
        this.id = id;
        this.name = name;
        this.subtype = subtype;
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

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CuisineType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subtype='" + subtype + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
