package com.example.wordgame;

public class HighScore {
    String username;
    int score;
    String date;

    public HighScore(String username, int score, String date) {
        this.username = username;
        this.score = score;
        this.date = date;
    }

    public String getUsername() { return username; }
    public int getScore() { return score; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return username + " - " + score + " pts (" + date + ")";
    }
}