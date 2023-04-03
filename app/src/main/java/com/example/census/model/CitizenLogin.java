package com.example.census.model;

import java.io.Serializable;

public class CitizenLogin implements Serializable {
    private int username_id;
    private String username;
    private String password;
    private String finger_print;
    private String facial_print;
    private int api_key;

    public CitizenLogin() {
    }

    public CitizenLogin(int username_id, String username, String password, String finger_print, String facial_print, int api_key) {
        this.username_id = username_id;
        this.username = username;
        this.password = password;
        this.finger_print = finger_print;
        this.facial_print = facial_print;
        this.api_key = api_key;
    }

    public int getUsername_id() {
        return username_id;
    }

    public void setUsername_id(int username_id) {
        this.username_id = username_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFinger_print() {
        return finger_print;
    }

    public void setFinger_print(String finger_print) {
        this.finger_print = finger_print;
    }

    public String getFacial_print() {
        return facial_print;
    }

    public void setFacial_print(String facial_print) {
        this.facial_print = facial_print;
    }

    public int getApi_key() {
        return api_key;
    }

    public void setApi_key(int api_key) {
        this.api_key = api_key;
    }
}
