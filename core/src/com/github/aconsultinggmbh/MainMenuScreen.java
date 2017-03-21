package com.github.aconsultinggmbh;

import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {

    private final ProjectY gm;

    public MainMenuScreen(ProjectY gm) {
        this.gm = gm;

        //The create method is now the Constructor
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //Example with batch and font
        gm.getBatch().begin();
        gm.getFont().draw(gm.getBatch(), "Hello",100,150);
        gm.getBatch().end();

        /*
        if(Gdx.input.isTouched()){

        }
        */

        boolean somethingHappened = true;
        if(somethingHappened) {
            gm.setScreen(new CreateGameScreen(gm));
            dispose();
            /*
            gm.setScreen(new JoinGameScreen(gm));
            dispose();
            gm.setScreen(new SettingsScreen(gm));
            dispose();
            */
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
