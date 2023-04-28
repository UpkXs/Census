package com.example.census.model;

public class HouseholdType {
    private int id;
    private String name;
    private int type;

    public HouseholdType() {
    }

    public HouseholdType(int id, String name, int type) {
        this.id   = id;
        this.name = name;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
