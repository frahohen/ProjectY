package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.aconsultinggmbh.utils.AcceleroButton;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.HomeButton;
import com.github.aconsultinggmbh.utils.RuhelageButton;
import com.github.aconsultinggmbh.utils.StyleHandler;

public class SettingsScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private CustomButton buttonBack, buttonCalib, buttonAccelero, buttonTMP2;
   // private BitmapFont font;

    public SettingsScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    private void create(){
        final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        stage = new Stage();

        atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        buttonCalib=new RuhelageButton();

        buttonAccelero=new AcceleroButton();



        // butttonTMP2 --> Platzhalter

        buttonTMP2 = new CustomButton("Template 2");
        buttonTMP2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");

                return super.touchDown(event, x, y, pointer, button);
            }
        });


        buttonBack = new HomeButton(screenManager);

        table.add(buttonCalib).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonAccelero).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonTMP2).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonBack).width(StyleHandler.getButtonWidth()).pad(10);
        //table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}
