package com.internetwarz.basketballrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.internetwarz.basketballrush.utils.GameDotValues;
import com.internetwarz.basketballrush.utils.GameUtils;
import com.internetwarz.basketballrush.utils.Score;
import com.internetwarz.basketballrush.utils.SimpleDirectionGestureDetector;

import java.util.Iterator;
import java.util.Random;

public class TwoBallMode implements Screen,InputProcessor{
    final BasketBallRush game;
    final float appWidth = 768;
    final float appHeight = 1280;
    SpriteBatch batch;
    OrthographicCamera camera;

    GlyphLayout layoutScore;

    //setting values on touch
    private static int gameSpeed;
    private static int touchCounter;

    private static int blueballs;
    private static int greenballs;

    Score score;
    private GameUtils gameutils;

    private String touchImage;
    private String startMessage;
    private String gameType;

    //setting variables to store two different color balls and the net
    private Texture playerDotImage;     //Contains net image resource
    private Texture gameDotImage;       //Contains ball 1 resource
    private Texture gameDotImage1;

    private Rectangle playerDotRectangle;
    private Array<GameDotValues> gameDot;

    //storing the time of last dot in nano seconds
    private long lastDotTime;


    //Code to show the tutorial animation
    private static final int        FRAME_COLS = 6;         // #1
    private static final int        FRAME_ROWS = 4;         // #2

    Animation walkAnimation;          // #3
    Texture walkSheet;              // #4
    TextureRegion[]                 walkFrames;             // #5
    TextureRegion                   currentFrame;           // #7

    float stateTime;                                        // #8

    public TwoBallMode(final BasketBallRush gam){
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, appWidth, appHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        layoutScore = new GlyphLayout();

        blueballs =0;
        greenballs =0;

        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(this);
        plex.addProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            //Chainging the ball color according to swipe action of the user.
            @Override
            public void onUp() {
            }

            @Override
            public void onRight() {
                touchCounter++;
                playerDotImage = game.assets.getTexture("net1");
                touchImage="image";
            }

            @Override
            public void onLeft() {
                touchCounter++;
                playerDotImage = game.assets.getTexture("net2");
                touchImage="image1";
            }

            @Override
            public void onDown() {

            }
        }));
        Gdx.input.setInputProcessor(plex);

        gameType="two color mode";
        gameSpeed = 600;
        touchCounter =0;
        gameutils = new GameUtils();
        score = new Score(0);
        touchImage = "image1";
        startMessage = "Swipe";

        //loading the images in the variables
        playerDotImage = game.assets.getTexture("net1");
        gameDotImage = game.assets.getTexture("ball1");
        gameDotImage1 = game.assets.getTexture("ball2");

        //placing the player dot in the middle of the screen
        playerDotRectangle = new Rectangle(appWidth/2 -playerDotImage.getWidth()/2,20,playerDotImage.getWidth(),playerDotImage.getHeight());

        gameDot = new Array<GameDotValues>();


        //animation code initialization
        walkSheet = game.assets.getTexture("two"); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.083f, walkFrames);      // #11
        stateTime = 0f;                         // #13
    }

    private void populateDots(){
        Rectangle dots = new Rectangle();
        dots.x = gameutils.randomLocation((int)appWidth);
        dots.y = appHeight;
        dots.width = gameDotImage.getWidth();
        dots.height = gameDotImage.getHeight();

        Random rand = new Random();
        int  n = rand.nextInt(2) + 1;
        GameDotValues g;
        if(n==1){
            g = new GameDotValues(dots,gameDotImage1,"image1");
        }
        else{
            g = new GameDotValues(dots,gameDotImage,"image");
        }
        gameDot.add(g);

        lastDotTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

        batch.begin();

        //Drawing the net image
        batch.draw(playerDotImage, playerDotRectangle.x, playerDotRectangle.y, playerDotRectangle.width, playerDotRectangle.height);

        layoutScore.setText(game.font,""+score.getStringScore());
        if(touchCounter >= 1) {
            for(GameDotValues gameDotValues : gameDot){
                game.font.draw(batch,score.getStringScore(),appWidth/2-layoutScore.width/2,90*(appHeight/100));
                batch.draw(gameDotValues.getTexture(),gameDotValues.getRectangle().x,gameDotValues.getRectangle().y);
            }
        }
        else{
            game.font.draw(batch,startMessage,appWidth/2-startMessage.length()*40/2,appHeight/2);
            batch.draw(currentFrame, appWidth/2-currentFrame.getRegionWidth()/2, 50);             // #17
        }

        batch.end();

        if(touchCounter >=1) {
            // check if we need to create a new dot
            if (TimeUtils.nanoTime() - lastDotTime > 700000000) {
                populateDots();
            }

            Iterator<GameDotValues> iter = gameDot.iterator();
            while (iter.hasNext()) {
                GameDotValues entry = iter.next();
                Rectangle dot = entry.getRectangle();

                //Updating the game speed as the game progresses
                gameSpeed = gameutils.updateGameSpeed(score.getScore(),gameSpeed,gameType);

                //Calculating the speed to decrease/increase x coordinate
                int xSpeed = gameutils.calXSpeed(gameSpeed,appHeight,appWidth);

                //Main Game Logic that moves the balls
                dot.y = dot.y - gameSpeed * Gdx.graphics.getDeltaTime();
                if (dot.x < appWidth / 2 - gameDotImage.getWidth() / 2) {
                    dot.x = dot.x + xSpeed * Gdx.graphics.getDeltaTime();
                } else if (dot.x > appWidth / 2 - gameDotImage.getWidth() / 2) {
                    dot.x = dot.x - xSpeed * Gdx.graphics.getDeltaTime();
                }
                if (dot.y + 64 < 0) {
                    iter.remove();
                }

                //Condition to check if the ball's color is same as the net's color
                if (dot.overlaps(playerDotRectangle)) {
                    if (entry.getString().equals(touchImage)) {
                        score.setScore(score.getScore()+1);
                        game.playServices.unlockAchievement(score.getScore(),gameType);
                        iter.remove();
                    }
                    else{
                        game.setScreen(new GameEndScreen(game,score,gameType,blueballs,greenballs));
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        game.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.setScreen(new GameModeSelect(game));
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

