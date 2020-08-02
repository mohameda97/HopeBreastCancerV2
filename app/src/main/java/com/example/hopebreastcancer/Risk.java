package com.example.hopebreastcancer;


public class Risk {
    public Risk(){

    }
    private String dateTime;
    private String q1,q2,q3,q4,q5,q6,q7,q8,q9,result;

    public Risk(String dateTime, String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9,String result) {
        this.dateTime = dateTime;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getQ1() {
        return q1;
    }

    public String getQ2() {
        return q2;
    }

    public String getQ3() {
        return q3;
    }

    public String getQ4() {
        return q4;
    }

    public String getQ5() {
        return q5;
    }

    public String getQ6() {
        return q6;
    }

    public String getQ7() {
        return q7;
    }

    public String getQ8() {
        return q8;
    }

    public String getQ9() {
        return q9;
    }
}
