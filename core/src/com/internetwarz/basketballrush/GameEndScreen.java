package com.internetwarz.basketballrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.internetwarz.basketballrush.utils.Score;

public class GameEndScreen implements Screen,InputProcessor {
    final BasketBallRush game;
    Score score;
    final float appWidth = 768;
    final float appHeight = 1280;
    SpriteBatch batch;
    OrthographicCamera camera;
    Sound clickSound;
    Preferences prefs;

    private String gameType;
    private String scoreString;
    private String highScoreString;
    private static int blueballs;
    private static int greenballs;

    GlyphLayout layoutGameOver,layoutYourScore,layoutHighScore;

    private Stage stage;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private ImageButton playButton,leaderboardButton,achievementsButton,homeButton;

    public GameEndScreen(final BasketBallRush gam, Score scor, String gt,int bb,int gb){
        this.game=gam;
        this.score = scor;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, appWidth, appHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        buttonAtlas = new TextureAtlas("buttons.pack");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonAtlas);
        stage = new Stage(new FitViewport(appWidth,appHeight));
        stage.clear();
        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);
        Gdx.input.setInputProcessor(plex);
        prefs = Gdx.app.getPreferences("My Preferences");
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clickSound.mp3"));

        scoreString = "Score : "+score.getStringScore();
        gameType = gt;

        this.blueballs = bb;
        this.greenballs = gb;

        //setting score preferences
        if(score.getScore()>prefs.getInteger(gameType,0)){
            prefs.putInteger(gameType,score.getScore());
        }
        prefs.flush();
        highScoreString = "Best : "+prefs.getInteger(gameType,0);
        game.playServices.submitScore(score.getScore(),gameType);

        //setting games Played preferences
        prefs.putInteger(gameType+" played",prefs.getInteger(gameType+" played",0)+1);

        prefs.flush();

        game.playServices.gamesPlayedAchievements(gameType,prefs.getInteger(gameType+" played",0));
        game.playServices.blueBallsCounter(blueballs);
        game.playServices.greenBallsCounter(greenballs);

        layoutGameOver = new GlyphLayout();
        layoutGameOver.setText(game.font,"Game Over");

        layoutYourScore = new GlyphLayout();
        layoutYourScore.setText(game.font,scoreString);

        layoutHighScore = new GlyphLayout();
        layoutHighScore.setText(game.font,highScoreString);


        //Play Button resources
        playButton = new ImageButton(buttonSkin.getDrawable("play"),buttonSkin.getDrawable("playClicked"));
        playButton.setPosition(appWidth/2-playButton.getWidth()/2,appHeight/2-playButton.getHeight()/2);
        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                if(gameType.equals("two color mode")){
                    game.setScreen(new TwoBallMode(game));
                }
                else if(gameType.equals("three color mode")){
                    game.setScreen(new ThreeBallMode(game));
                }
                else if(gameType.equals("four color mode")){
                    game.setScreen(new FourBallMode(game));
                }
                else{
                    game.setScreen(new TwoBallMode(game));
                }
            }
        });
        stage.addActor(playButton);

        //Leaderboard Button resources
        leaderboardButton = new ImageButton(buttonSkin.getDrawable("leaderboard"),buttonSkin.getDrawable("leaderboardClicked"));
        leaderboardButton.setPosition(widthPercent(30)-leaderboardButton.getWidth()/2,heightPercent(35));
        leaderboardButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.playServices.showScore();
            }
        });
        stage.addActor(leaderboardButton);

        //Achievements Button resources
        achievementsButton = new ImageButton(buttonSkin.getDrawable("achievements"),buttonSkin.getDrawable("achievementsClicked"));
        achievementsButton.setPosition(widthPercent(70)-achievementsButton.getWidth()/2,heightPercent(35));
        achievementsButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.playServices.showAchievement();
            }
        });
        stage.addActor(achievementsButton);

        //Home Button resources
        homeButton = new ImageButton(buttonSkin.getDrawable("home"),buttonSkin.getDrawable("homeClicked"));
        homeButton.setPosition(appWidth/2-homeButton.getWidth()/2, heightPercent(25));
        homeButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(homeButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        batch.begin();
        stage.draw();
        batch.end();

        batch.begin();
        game.font.draw(batch,"GAME OVER",appWidth/2-layoutGameOver.width/2,heightPercent(80));
        game.font.draw(batch,scoreString,appWidth/2-layoutYourScore.width/2,heightPercent(70));
        game.font.draw(batch,highScoreString,appWidth/2-layoutHighScore.width/2,heightPercent(60));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        buttonSkin.dispose();
        buttonAtlas.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
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

    public float widthPercent(int w){
        float result;
        result = (appWidth*w)/100;
        return result;
    }

    public float heightPercent(int h){
        float result;
        result = (appHeight*h)/100;
        return result;
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
