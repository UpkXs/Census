package com.example.census.model;

public enum Role {
    ADMIN("Admin"),
    STATIONARY("Stationary"),
    CONTROLLER("Controller"),
    CITIZEN("Citizen");

    public final String label;

    private Role(String label) {
        this.label = label;
    }
}
