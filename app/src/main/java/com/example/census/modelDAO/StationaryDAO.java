package com.example.census.modelDAO;

public class StationaryDAO {

    private int stationary_id;
    private String stationary_username;
    private int region_id;

    public StationaryDAO() {
    }

    public StationaryDAO(int stationary_id, String stationary_username, int region_id) {
        this.stationary_id = stationary_id;
        this.stationary_username = stationary_username;
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

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
