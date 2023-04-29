package com.example.census.model;

import com.example.census.enums.AnswerType;

public class Question {
    private int        id;
    private String     question;
    private AnswerType answerType;
    private String answer;

    public Question() {
    }

    public Question(int id, String question, AnswerType answerType, String answer) {
        this.id         = id;
        this.question   = question;
        this.answerType = answerType;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
