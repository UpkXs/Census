package com.example.census.model;

import java.io.Serializable;

public class Citizen implements Serializable {
    private int citizen_tin;
    private String citizen_fullName;
    private int username_id;
    private int region_id;

    public Citizen() {
    }

    public Citizen(int citizen_tin, String citizen_fullName, int username_id, int region_id) {
        this.citizen_tin = citizen_tin;
        this.citizen_fullName = citizen_fullName;
        this.username_id = username_id;
        this.region_id = region_id;
    }

    public int getCitizen_tin() {
        return citizen_tin;
    }

    public void setCitizen_tin(int citizen_tin) {
        this.citizen_tin = citizen_tin;
    }

    public String getCitizen_fullName() {
        return citizen_fullName;
    }

    public void setCitizen_fullName(String citizen_fullName) {
        this.citizen_fullName = citizen_fullName;
    }

    public int getUsername_id() {
        return username_id;
    }

    public void setUsername_id(int username_id) {
        this.username_id = username_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
