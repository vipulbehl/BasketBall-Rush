package com.internetwarz.basketballrush.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    //Image Resources
    Texture ball1,ball2,ball3,ball4,net1,net2,net3,net4,gameName,logo,tutButton,tutButtonClicked,
            spriteSheetFour,spriteSheetThree,spriteSheetTwo,tutorial;
    TextureAtlas buttonAtlas;

    //Sound Resources
    Sound clickSound;

    public void load(){
        ball1 = new Texture(Gdx.files.internal("images/Ball1.png"));
        ball2 = new Texture(Gdx.files.internal("images/Ball2.png"));
        ball3 = new Texture(Gdx.files.internal("images/Ball3.png"));
        ball4 = new Texture(Gdx.files.internal("images/Ball4.png"));
        net1 = new Texture(Gdx.files.internal("images/Net1.png"));
        net2 = new Texture(Gdx.files.internal("images/Net2.png"));
        net3 = new Texture(Gdx.files.internal("images/Net3.png"));
        net4 = new Texture(Gdx.files.internal("images/Net4.png"));
        gameName = new Texture(Gdx.files.internal("images/gameName.png"));
        logo = new Texture(Gdx.files.internal("images/internetwarz.png"));
        tutButton = new Texture(Gdx.files.internal("images/tutButton.png"));
        tutButtonClicked = new Texture(Gdx.files.internal("images/tutButtonClicked.png"));
        spriteSheetFour = new Texture(Gdx.files.internal("images/SpriteSheetFourImage.png"));
        spriteSheetThree = new Texture(Gdx.files.internal("images/SpriteSheetThreeImage.png"));
        spriteSheetTwo = new Texture(Gdx.files.internal("images/SpriteSheetTwoImage.png"));
        tutorial = new Texture(Gdx.files.internal("images/tutorial.png"));

        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clickSound.mp3"));
    }

    public Texture getTexture(String file){
        if (file.equals("ball1")) {
            return ball1;
        }
        else if(file.equals("ball2")){
            return  ball2;
        }
        else if(file.equals("ball3")){
            return  ball3;
        }
        else if(file.equals("ball4")){
            return  ball4;
        }
        else if(file.equals("net1")){
            return  net1;
        }
        else if(file.equals("net2")){
            return  net2;
        }
        else if(file.equals("net3")){
            return  net3;
        }
        else if(file.equals("net4")){
            return  net4;
        }
        else if(file.equals("gameName")){
            return  gameName;
        }
        else if(file.equals("internetwarz")){
            return  logo;
        }
        else if(file.equals("tutButton")){
            return  tutButton;
        }
        else if(file.equals("tutButtonClicked")){
            return  tutButtonClicked;
        }
        else if(file.equals("four")){
            return  spriteSheetFour;
        }
        else if(file.equals("three")){
            return  spriteSheetThree;
        }
        else if(file.equals("two")){
            return  spriteSheetTwo;
        }
        else {
            return  tutorial;
        }
    }

    public Sound getSound(){
        return clickSound;
    }

    public TextureAtlas getButtonAtlas(){
        return buttonAtlas;
    }

    public void dispose(){
        ball1.dispose();
        ball2.dispose();
        ball3.dispose();
        ball4.dispose();
        net1.dispose();
        net2.dispose();
        net3.dispose();
        net4.dispose();
        gameName.dispose();
        logo.dispose();
        tutButton.dispose();
        tutButtonClicked.dispose();
        spriteSheetTwo.dispose();
        spriteSheetThree.dispose();
        spriteSheetFour.dispose();
        tutorial.dispose();
        buttonAtlas.dispose();
        clickSound.dispose();
    }
}
