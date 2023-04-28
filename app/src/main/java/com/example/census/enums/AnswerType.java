package com.example.census.enums;

public enum AnswerType {
    TEXT("Text"),
    NUMBER("Number");

    public final String label;

    private AnswerType(String label) {
        this.label = label;
    }
}
