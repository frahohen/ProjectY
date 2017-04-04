package com.github.aconsultinggmbh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.aconsultinggmbh.map.GameMap;

public class LevelScreen implements Screen {

    private final ProjectY gm;

    private GameMap map;
    private OrthographicCamera camera;
    private float scale = 1.0f;

    public LevelScreen(ProjectY gm) {
        this.gm = gm;

        //The create method is now the Constructor
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        map = new GameMap("maputils/bigMap.tmx",scale);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.100f, 0.314f, 0.314f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        map.render(camera);
        map.showBounds(true,camera);
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

    }
}
