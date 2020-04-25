package com.shockdee.studqr.models;

public class Grade {

    public int grade_ID;
    public String grade_name;

    public Grade() {
    }

    public Grade(String grade_name) {
        this.grade_name = grade_name;
    }

    public Grade(int grade_ID, String grade_name) {
        this.grade_ID = grade_ID;
        this.grade_name = grade_name;
    }
}
