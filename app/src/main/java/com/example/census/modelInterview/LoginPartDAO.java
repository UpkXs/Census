package com.example.census.modelInterview;

public class LoginPartDAO {
    private int tin;
    private String fullName;
    private String regionName;

    public LoginPartDAO() {
    }

    public LoginPartDAO(int tin, String fullName, String regionName) {
        this.tin        = tin;
        this.fullName = fullName;
        this.regionName = regionName;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
