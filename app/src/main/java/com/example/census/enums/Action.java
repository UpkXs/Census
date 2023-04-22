package com.example.census.enums;

public enum Action {
    VIEW("View"),
    DELETE("Delete"),
    CHANGE("Change");

    public final String label;

    private Action(String label) {
        this.label = label;
    }
}
