package com.github.aconsultinggmbh.screen;

/**
 * Created by Alex on 09.05.2017.
 */

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.github.aconsultinggmbh.utils.HomeButton;
import com.github.aconsultinggmbh.utils.StyleHandler;


public class MultiplayerMenuScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonHostGame, buttonJoinGame;
    private BitmapFont font;
    private TextField tfIpAddress;

    public MultiplayerMenuScreen(ProjectY screenManager) {
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

        font = new BitmapFont();
        font.getData().setScale(5.0f);




        buttonHostGame = new TextButton("Join Game", StyleHandler.getButtonStyle());
        buttonHostGame.pad(20);
        buttonHostGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Join Game");
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input (String text) {
                        //TEXT = IP ADDRESS für Multiplayer zum verwenden

                        boolean validIP=true;
                        String [] x =text.split("\\.");
                        if(x.length!=4){
                            validIP=false;
                        }
                        for(String y:x) {
                            try {
                                int i = Integer.parseInt(y);
                                if (i < 0 || i > 255) {
                                    validIP = false;
                                }
                            } catch (Exception e) {
                                validIP = false;
                            }
                        }

                       if(validIP){
                            //Verbindung hier aufbauen...
                            screenManager.setScreen(new LobbyScreen(screenManager,false));
                       }else{
                           //ERRORMESSAGE ANZEIGEN(muss ich noch machen)
                       }
                    }
                    @Override
                    public void canceled () {
                        //??????? Hat keine Funktion muss aber wegen abstract-override da sein (Vlt kommt ja noch ein sinn xD)
                    }
                }, "Enter Host-IP-Address", "192.168.0.1", "Enter here");


                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonJoinGame = new TextButton("Host game", StyleHandler.getButtonStyle());
        buttonJoinGame.pad(20);
        buttonJoinGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Host game");
                screenManager.setScreen(new LobbyScreen(screenManager,true));
                return super.touchDown(event, x, y, pointer, button);
            }
        });



        buttonExit = new HomeButton(screenManager);
        buttonExit.pad(20);


        table.add(buttonHostGame).width(600).pad(10);
        table.row();
        table.add(buttonJoinGame).width(600).pad(10);
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
