package com.internetwarz.basketballrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen implements Screen {
    final BasketBallRush game;
    final float appWidth = 768;
    final float appHeight = 1280;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture splashImage;
    long startTime;

    public SplashScreen(final BasketBallRush gam){
        this.game = gam;
        startTime = TimeUtils.millis();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, appWidth, appHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        splashImage = new Texture(Gdx.files.internal("images/internetwarz.png"));

        //Calling the load functions to load all the assets on the splash screen
        game.assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImage,appWidth/2-splashImage.getWidth()/2,appHeight/2-splashImage.getHeight()/2);
        batch.end();

        if(TimeUtils.millis() - startTime > 2000)
            game.setScreen(new MainMenuScreen(game));
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
    public void dispose() {
        batch.dispose();
    }
}
