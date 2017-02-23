package com.internetwarz.basketballrush.utils;

//Bean class to maintain score in the game
public class Score {
    private int score;

    public Score(){
    }

    public Score(int s){
        score = s;
    }

    public void setScore(int s){
        score = s;
    }

    public int getScore(){
        return score;
    }

    public String getStringScore(){
        String scoreString = Integer.toString(score);
        return scoreString;
    }
}
