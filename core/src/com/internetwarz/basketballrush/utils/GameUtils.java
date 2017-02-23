package com.internetwarz.basketballrush.utils;

import java.util.Random;

//Class that contains in game functionality

public class GameUtils {

    public int calXSpeed(int gameSpeed, float height, float width){
        float h = height-85;
        float seconds = h/gameSpeed;
        float distance = width/2-64/2;
        float speed = distance/seconds;
        return Math.round(speed);
    }

    public int randomLocation(int width){
        int[] locations = new int[3];
        locations[0]=0;
        locations[1]=width/2-64/2;
        locations[2]=width-64;

        Random generator = new Random();
        int randomIndex = generator.nextInt(locations.length);
        return locations[randomIndex];
    }

    public int updateGameSpeed(int score, int gameSpeed,String gameType){
        if(gameType.equals("two color mode")){
            if(score <10){
                return 700;
            }
            else if(score<20){
                return 800;
            }
            else if(score<30){
                return 900;
            }
            else if(score<40)
            {
                return 1000;
            }
            else if(score<50)
            {
                return 1200;
            }
            else if(score<70)
            {
                return 1300;
            }
            else if(score<100){
                return 1600;
            }
            else{
                return gameSpeed;
            }
        }
        else if(gameType.equals("three color mode")){
            if(score <10){
                return 800;
            }
            else if(score<20){
                return 900;
            }
            else if(score<30){
                return 1000;
            }
            else if(score<40)
            {
                return 1100;
            }
            else if(score<50)
            {
                return 1300;
            }
            else if(score<70)
            {
                return 1400;
            }
            else if(score<100){
                return 1700;
            }
            else{
                return gameSpeed;
            }
        }
        else if(gameType.equals("four color mode")){
            if(score <10){
                return 900;
            }
            else if(score<20){
                return 1000;
            }
            else if(score<30){
                return 1100;
            }
            else if(score<40)
            {
                return 1200;
            }
            else if(score<50)
            {
                return 1400;
            }
            else if(score<70)
            {
                return 1500;
            }
            else if(score<100){
                return 1800;
            }
            else{
                return gameSpeed;
            }
        }
       return gameSpeed;
    }
}
