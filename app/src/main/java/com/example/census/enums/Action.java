package com.example.census.enums;

public enum Action {
    VIEW("View"),
    DELETE("Delete"),
    CHANGE("Change"),
    CHANGE_ADMIN("Change Admin");

    public final String label;

    private Action(String label) {
        this.label = label;
    }
}
