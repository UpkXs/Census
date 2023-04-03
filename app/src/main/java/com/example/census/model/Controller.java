package com.example.census.model;

public class Controller {
    private int controller_id;
    private String controller_name;
    private String controller_username;
    private String controller_password;
    private int controller_apikey;
    private int region_id;

    public Controller() {
    }

    public Controller(int controller_id, String controller_name, String controller_username, String controller_password, int controller_apikey, int region_id) {
        this.controller_id = controller_id;
        this.controller_name = controller_name;
        this.controller_username = controller_username;
        this.controller_password = controller_password;
        this.controller_apikey = controller_apikey;
        this.region_id = region_id;
    }

    public int getController_id() {
        return controller_id;
    }

    public void setController_id(int controller_id) {
        this.controller_id = controller_id;
    }

    public String getController_name() {
        return controller_name;
    }

    public void setController_name(String controller_name) {
        this.controller_name = controller_name;
    }

    public String getController_username() {
        return controller_username;
    }

    public void setController_username(String controller_username) {
        this.controller_username = controller_username;
    }

    public String getController_password() {
        return controller_password;
    }

    public void setController_password(String controller_password) {
        this.controller_password = controller_password;
    }

    public int getController_apikey() {
        return controller_apikey;
    }

    public void setController_apikey(int controller_apikey) {
        this.controller_apikey = controller_apikey;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
