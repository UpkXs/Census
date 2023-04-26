package com.example.census.modelDAO;

public class CitizenDAO {
    private String fullName;
    private String username;
    private int TIN;

    public CitizenDAO() {
    }

    public CitizenDAO(String fullName, String username, int TIN) {
        this.fullName = fullName;
        this.username = username;
        this.TIN      = TIN;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTIN() {
        return TIN;
    }

    public void setTIN(int TIN) {
        this.TIN = TIN;
    }
}

