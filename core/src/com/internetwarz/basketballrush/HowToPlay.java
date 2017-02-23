package com.internetwarz.basketballrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HowToPlay implements Screen,InputProcessor {

    final BasketBallRush game;
    final float appWidth = 768;
    final float appHeight = 1280;
    SpriteBatch batch;
    OrthographicCamera camera;
    Sound clickSound;
    Preferences prefs;

    Texture tutorial,playButton;
    Rectangle playRectangle;

    public HowToPlay(final BasketBallRush gam){
        this.game=gam;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, appWidth, appHeight);

        prefs = Gdx.app.getPreferences("My Preferences");

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        clickSound = game.assets.getSound();

        tutorial = game.assets.getTexture("tutorial");
        playButton = game.assets.getTexture("tutButton");
        playRectangle = new Rectangle(appWidth/2-playButton.getWidth()/2,0,playButton.getWidth(),playButton.getHeight());

        prefs.putBoolean("first",false);
        prefs.flush();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(tutorial,0,0);
        batch.draw(playButton,appWidth/2-playButton.getWidth()/2,0);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float widthPara = appWidth/Gdx.graphics.getWidth();
        float heightPara = appHeight/Gdx.graphics.getHeight();
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        if(playRectangle.contains((touchPos.x)*(widthPara),(Gdx.graphics.getHeight() - touchPos.y)*(heightPara))){
            playButton = game.assets.getTexture("tutButtonClicked");
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float widthPara = appWidth/Gdx.graphics.getWidth();
        float heightPara = appHeight/Gdx.graphics.getHeight();
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        if(playRectangle.contains((touchPos.x)*(widthPara),(Gdx.graphics.getHeight() - touchPos.y)*(heightPara))){
            if(prefs.getBoolean("soundOn",true))
                clickSound.play();
            playButton = game.assets.getTexture("tutButton");
            game.setScreen(new GameModeSelect(game));
        }
        else{
            playButton = game.assets.getTexture("tutButton");
        }
        return true;
    }

    @Override
    public void dispose() {
        batch.dispose();
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
}
