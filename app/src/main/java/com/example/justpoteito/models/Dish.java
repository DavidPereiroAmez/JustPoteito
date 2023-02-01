package com.example.justpoteito.models;

import android.media.Image;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private int prepTime;
    private int idCuisineType;
    private String subtype;
    private String recipe;
    private String image;

    public Dish() {
    }

    public Dish(int id, String name, int prepTime, int idCuisineType, String subtype, String recipe) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
        this.idCuisineType = idCuisineType;
        this.subtype = subtype;
        this.recipe = recipe;
    }
    public Dish(int id, String name, int prepTime, int idCuisineType, String subtype, String recipe, String image) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
        this.idCuisineType = idCuisineType;
        this.subtype = subtype;
        this.recipe = recipe;
        this.image = image;
    }

    public Dish(int id, String name, String subtype) {
        this.id = id;
        this.name = name;
        this.subtype = subtype;
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

    public String getrecipe() {
        return recipe;
    }

    public void setrecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prepTime=" + prepTime +
                ", idCuisineType=" + idCuisineType +
                ", subtype='" + subtype + '\'' +
                ", recipe='" + recipe + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
