package com.example.justpoteito.models;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private int prepTime;
    private int idCuisineType;
    private String subtype;
    private String alergens;

    public Dish() {
    }

    public Dish(int id, String name, int prepTime, int idCuisineType, String subtype, String alergens) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
        this.idCuisineType = idCuisineType;
        this.subtype = subtype;
        this.alergens = alergens;
    }

    public Dish(int id, String name, int prepTime) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
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

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getIdCuisineType() {
        return idCuisineType;
    }

    public void setIdCuisineType(int idCuisineType) {
        this.idCuisineType = idCuisineType;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getAlergens() {
        return alergens;
    }

    public void setAlergens(String alergens) {
        this.alergens = alergens;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prepTime=" + prepTime +
                ", idCuisineType=" + idCuisineType +
                ", subtype='" + subtype + '\'' +
                ", alergens='" + alergens + '\'' +
                '}';
    }
}
