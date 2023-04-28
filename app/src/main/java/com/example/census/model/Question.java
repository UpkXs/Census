package com.example.census.model;

import com.example.census.enums.AnswerType;

public class Question {
    private int        id;
    private String     question;
    private AnswerType answerType;

    public Question() {
    }

    public Question(int id, String question, AnswerType answerType) {
        this.id         = id;
        this.question   = question;
        this.answerType = answerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }
}
