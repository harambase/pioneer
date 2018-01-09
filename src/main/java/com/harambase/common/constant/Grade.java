package com.harambase.common.constant;

public enum Grade {

    A("A", 4.0),
    AB("AB", 3.5),
    B("B", 3.0),
    BC("BC", 2.5),
    C("C", 2.0),
    CD("CD", 1.5),
    D("D", 1.0),
    F("F", 0);


    private String grade;
    private double point;

    Grade(String grade, double point) {
        this.grade = grade;
        this.point = point;
    }

    public String getGrade() {
        return grade;
    }

    public double getPoint() {
        return point;
    }

}