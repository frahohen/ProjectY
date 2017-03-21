package com.github.aconsultinggmbh;

import com.badlogic.gdx.Screen;

public class CreateGameScreen implements Screen {

    private final ProjectY gm;

    public CreateGameScreen(ProjectY gm) {
        this.gm = gm;

        //The create method is now the Constructor
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        boolean somethingHappened = true;
        if(somethingHappened) {
            gm.setScreen(new LevelScreen(gm));
            dispose();
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

    }
}
