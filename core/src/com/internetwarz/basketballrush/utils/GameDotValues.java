package com.internetwarz.basketballrush.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


//Bean class to store which ball is of which color
public class GameDotValues {

    Rectangle rectangle;
    Texture texture;
    String string;

    public GameDotValues(){

    }

    public GameDotValues(Rectangle r, Texture t, String s){
        rectangle =r;
        texture =t;
        string = s;
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public Texture getTexture(){
        return texture;
    }

    public String getString(){
        return string;
    }
}
