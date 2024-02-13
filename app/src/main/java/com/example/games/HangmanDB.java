package com.example.games;

public class HangmanDB
{
    String word, status;

    public HangmanDB(String word, String status) {
        this.word = word;
        this.status = status;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
