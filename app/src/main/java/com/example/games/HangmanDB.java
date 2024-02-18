package com.example.games;

public class HangmanDB
{
    String word, status, time1, time2, hint, lettersEnteredAlready, result, hangmanFig;
    int playerCount;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HangmanDB(String word, String status, int playerCount, String time1, String time2, String hint, String lettersEnteredAlready, String result, String hangmanFig) {
        this.word = word;
        this.status = status;
        this.playerCount=playerCount;
        this.time1=time1;
        this.time2=time2;
        this.hint=hint;
        this.lettersEnteredAlready=lettersEnteredAlready;
        this.result=result;
        this.hangmanFig=hangmanFig;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getLettersEnteredAlready() {
        return lettersEnteredAlready;
    }

    public void setLettersEnteredAlready(String lettersEnteredAlready) {
        this.lettersEnteredAlready = lettersEnteredAlready;
    }

    public String getTime2() {
        return time2;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getStatus() {
        return status;
    }

    public String getHangmanFig() {
        return hangmanFig;
    }

    public void setHangmanFig(String hangmanFig) {
        this.hangmanFig = hangmanFig;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
