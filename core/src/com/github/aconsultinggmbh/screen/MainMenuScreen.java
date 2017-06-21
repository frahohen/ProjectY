package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.StyleHandler;


public class MainMenuScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private CustomButton buttonExit, buttonCreateGame, buttonJoinGame, buttonSettings;
    //private BitmapFont font;
    private Sound menuSound;

    public MainMenuScreen(ProjectY screenManager) {
       menuSound=Gdx.audio.newSound(Gdx.files.internal("heartbeat.wav"));
        this.screenManager = screenManager;
        create();
    }

    private void create(){

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        stage = new Stage();

       atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        buttonCreateGame = new CustomButton("Singleplayer");
        buttonCreateGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Create Game");
                screenManager.setScreen(new GameScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonJoinGame = new CustomButton("Multiplayer");
        buttonJoinGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");
                screenManager.setScreen(new MultiplayerMenuScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonSettings = new CustomButton("Einstellungen");
        buttonSettings.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");
                screenManager.setScreen(new SettingsScreen(screenManager)); // öffnen den Einstellungs View
                return super.touchDown(event, x, y, pointer, button); //Button irgendwas
            }
        });

        buttonExit = new CustomButton("Spiel Beenden");
        buttonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Exit Game");
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.add(buttonCreateGame).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonJoinGame).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonSettings).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonExit).width(StyleHandler.getButtonWidth()).pad(10);
        //table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
      //  menuSound.loop(1f);
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
