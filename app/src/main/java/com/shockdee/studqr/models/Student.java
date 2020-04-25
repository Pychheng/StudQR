package com.shockdee.studqr.models;

import androidx.annotation.NonNull;

public class Student {

    public int studentID;
    public String first_name;
    public String last_name;
    public String email;
    public String grade;
    public int promotion;

    public Student() {
    }

    public Student(String first_name, String last_name, String email, String grade, int promotion) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.grade = grade;
        this.promotion = promotion;
    }

    public Student(int studentID, String first_name, String last_name, String email, String grade, int promotion) {
        this.studentID = studentID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.grade = grade;
        this.promotion = promotion;
    }

    @NonNull
    @Override
    public String toString() {
        return "First Name : "+this.first_name+'\n'
                +"Last Name : "+this.last_name+'\n'
                +"Email : "+this.email+'\n'
                +this.grade+" "+this.promotion;
    }
}
