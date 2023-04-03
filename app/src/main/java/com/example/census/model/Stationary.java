package com.example.census.model;

public class Stationary {
    private int stationary_id;
    private String stationary_username;
    private String stationary_password;
    private int stationary_apikey;
    private int region_id;

    public Stationary() {
    }

    public Stationary(int stationary_id, String stationary_username, String stationary_password, int stationary_apikey, int region_id) {
        this.stationary_id = stationary_id;
        this.stationary_username = stationary_username;
        this.stationary_password = stationary_password;
        this.stationary_apikey = stationary_apikey;
        this.region_id = region_id;
    }

    public int getStationary_id() {
        return stationary_id;
    }

    public void setStationary_id(int stationary_id) {
        this.stationary_id = stationary_id;
    }

    public String getStationary_username() {
        return stationary_username;
    }

    public void setStationary_username(String stationary_username) {
        this.stationary_username = stationary_username;
    }

    public String getStationary_password() {
        return stationary_password;
    }

    public void setStationary_password(String stationary_password) {
        this.stationary_password = stationary_password;
    }

    public int getStationary_apikey() {
        return stationary_apikey;
    }

    public void setStationary_apikey(int stationary_apikey) {
        this.stationary_apikey = stationary_apikey;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
