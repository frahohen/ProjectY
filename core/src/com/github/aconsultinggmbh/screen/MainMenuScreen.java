package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.aconsultinggmbh.utils.StyleHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainMenuScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonCreateGame, buttonJoinGame, buttonSettings;
    //private BitmapFont font;

    public MainMenuScreen(ProjectY screenManager) {
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

        /*  font = new BitmapFont();
        font.getData().setScale(5.0f);

      TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;*/

        buttonCreateGame = new TextButton("Singleplayer", StyleHandler.getButtonStyle());
        buttonCreateGame.pad(20);
        buttonCreateGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Create Game");
                screenManager.setScreen(new GameScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonJoinGame = new TextButton("Multiplayer", StyleHandler.getButtonStyle());
        buttonJoinGame.pad(20);
        buttonJoinGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");
                screenManager.setScreen(new MultiplayerMenuScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonSettings = new TextButton("Einstellungen", StyleHandler.getButtonStyle());
        buttonSettings.pad(20);
        buttonSettings.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");

                screenManager.setScreen(new SettingsScreen(screenManager)); // öffnen den Einstellungs View
                return super.touchDown(event, x, y, pointer, button); //Button irgendwas
            }
        });

        buttonExit = new TextButton("Spiel Beenden", StyleHandler.getButtonStyle());
        buttonExit.pad(20);
        buttonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Exit Game");
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.add(buttonCreateGame).width(600).pad(10);
        table.row();
        table.add(buttonJoinGame).width(600).pad(10);
        table.row();
        table.add(buttonSettings).width(600).pad(10);
        table.row();
        table.add(buttonExit).width(600).pad(10);
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
