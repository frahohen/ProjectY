package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Philip on 22.05.2017.
 */

public class SplashScreen implements Screen{

    private final ProjectY screenManager;

    private SpriteBatch batch;
    private Sprite splash;
    private boolean flag = true;
    private long start, end;

    public SplashScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    private void create(){
        batch = new SpriteBatch();
        Texture texture = new Texture("data/splash.png");
        splash = new Sprite(texture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();;
        splash.draw(batch);
        batch.end();

        if(flag){
            flag = false;
            start = System.currentTimeMillis();
        }
        end = System.currentTimeMillis();

        //5 sec anzeigen
        if((end - start) >= 5000) {
            flag= true;
            screenManager.setScreen(new MainMenuScreen(screenManager));

        }

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
