package com.example.justpoteito.models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int id;
    private String name;
    private String type;
    private String amount;

    public Ingredient() {
    }

    public Ingredient(int id, String name, String type, String amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
