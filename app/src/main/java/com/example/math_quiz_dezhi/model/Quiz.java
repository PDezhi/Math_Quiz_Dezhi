package com.example.math_quiz_dezhi.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Quiz implements Serializable, Comparable {
    // Basic operation
    private String operation;
    // The right answer of operation
    private float rightAnswer;
    // the answer inputted by user
    private float userAnswer;
    // the result if the answer user inputted equals to the right answer
    private String result;

    // constructor
    public Quiz(String operation, float rightAnswer, float userAnswer, String result) {
        this.operation = operation;
        this.rightAnswer = rightAnswer;
        this.userAnswer = userAnswer;
        this.result = result;
    }

    // getters and setters generated automatically
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public float getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(float rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public float getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(float userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "operation='" + operation + '\'' +
                ", rightAnswer=" + rightAnswer +
                ", userAnswer=" + userAnswer +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Quiz otherObject = (Quiz) o;
        return this.result.compareTo(otherObject.result);
    }
}
