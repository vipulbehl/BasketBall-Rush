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

public class GameModeSelect implements Screen,InputProcessor {
    final BasketBallRush game;
    final float appWidth = 768;
    final float appHeight = 1280;
    SpriteBatch batch;
    OrthographicCamera camera;
    Sound clickSound;
    Preferences prefs;
    GlyphLayout gameMode;

    private Stage stage;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private ImageButton easyButton,mediumButton,hardButton;

    public GameModeSelect(final BasketBallRush gam){
        this.game=gam;

        //Setting up the camera and graphic resources
        camera = new OrthographicCamera();
        camera.setToOrtho(false, appWidth, appHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        buttonAtlas = game.assets.getButtonAtlas();
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonAtlas);
        stage = new Stage(new FitViewport(appWidth,appHeight));
        stage.clear();

        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);

        Gdx.input.setInputProcessor(plex);
        Gdx.input.setCatchBackKey(true);

        prefs = Gdx.app.getPreferences("My Preferences");
        clickSound = game.assets.getSound();
        gameMode = new GlyphLayout();
        gameMode.setText(game.font,"Select Game Mode");

        //Easy Button resources
        easyButton = new ImageButton(buttonSkin.getDrawable("easy"),buttonSkin.getDrawable("easyClicked"));
        easyButton.setPosition(appWidth/2-easyButton.getWidth()/2,heightPercent(60));
        easyButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.setScreen(new TwoBallMode(game));
            }
        });
        stage.addActor(easyButton);

        //Medium Button resources
        mediumButton = new ImageButton(buttonSkin.getDrawable("medium"),buttonSkin.getDrawable("mediumClicked"));
        mediumButton.setPosition(appWidth/2-mediumButton.getWidth()/2,appHeight/2);
        mediumButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.setScreen(new ThreeBallMode(game));
            }
        });
        stage.addActor(mediumButton);

        //Hard Button resources
        hardButton = new ImageButton(buttonSkin.getDrawable("hard"),buttonSkin.getDrawable("hardClicked"));
        hardButton.setPosition(appWidth/2-hardButton.getWidth()/2,heightPercent(40));
        hardButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(prefs.getBoolean("soundOn",true))
                    clickSound.play();
                game.setScreen(new FourBallMode(game));
            }
        });
        stage.addActor(hardButton);
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
        game.font.draw(batch,"Select Game Mode",appWidth/2-gameMode.width/2,heightPercent(80));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        buttonSkin.dispose();
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
            game.setScreen(new MainMenuScreen(game));
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
