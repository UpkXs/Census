package com.example.census.model;

public class Household {
    private int    id;
    private int    citizen_tin;
    private String address;
    private int    type;
    private int    region;
    private int    year;
    private int    floor;
    private String material;
    private String landscape;
    private int    size;
    private int    wo_size;
    private int    room;
    private String owner;

    public Household() {
    }

    public Household(int id,
                     int citizen_tin,
                     String address,
                     int type,
                     int region,
                     int year,
                     int floor,
                     String material,
                     String landscape,
                     int size,
                     int wo_size,
                     int room,
                     String owner) {
        this.id          = id;
        this.citizen_tin = citizen_tin;
        this.address     = address;
        this.type        = type;
        this.region      = region;
        this.year        = year;
        this.floor       = floor;
        this.material    = material;
        this.landscape   = landscape;
        this.size        = size;
        this.wo_size     = wo_size;
        this.room        = room;
        this.owner       = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCitizen_tin() {
        return citizen_tin;
    }

    public void setCitizen_tin(int citizen_tin) {
        this.citizen_tin = citizen_tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWo_size() {
        return wo_size;
    }

    public void setWo_size(int wo_size) {
        this.wo_size = wo_size;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
