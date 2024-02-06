package com.example.games;

public class FlamesDB {

    String a, b, result;

    public FlamesDB() {
    }

    public FlamesDB(String a, String b, String result) {
        this.a = a;
        this.b = b;
        this.result=result;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

