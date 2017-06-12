package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.CustomLabel;
import com.github.aconsultinggmbh.utils.HomeButton;
import com.github.aconsultinggmbh.utils.StyleHandler;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Alex on 09.05.2017.
 */

public class LobbyScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private CustomButton buttonExit, buttonStartGame;
   // private BitmapFont font;
    private CustomLabel labelInfo1,labelInfo2,labelInfo3,labelPlayerConnected;

    public LobbyScreen(ProjectY screenManager,boolean host) {
        this.screenManager = screenManager;
        create(host);
    }

    private void create(final boolean host){

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        stage = new Stage();

        atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        labelInfo1 = new CustomLabel("Willkommen in der Lobby!");
        labelInfo2 = new CustomLabel("Warten auf Spieler...");
        labelInfo3 = new CustomLabel("");
        //Fraglich?? wollen wir haben, dass er anzeigt wieviele spieler gerade in der Lobby sind?
        labelPlayerConnected  = new CustomLabel("Spieler verbunden:---");
        if(host){

            try {
                ArrayList<String> addresses = new ArrayList<String>();
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                for(NetworkInterface ni : Collections.list(interfaces)){
                    for(InetAddress address : Collections.list(ni.getInetAddresses()))
                    {
                        if(address instanceof Inet4Address){
                            addresses.add(address.getHostAddress());
                            //Im Emulator wird immer 127.0.0.1 angezeigt,
                            // auf meinem handy hat das mit der addresse funktioniert(auch wenn ein bisschen overkill hier ist mit der schleife, etc)
                        }
                    }
                }
                labelInfo3.setText("Host-IP: "+addresses.get(0));

            }catch(Exception e){
               labelInfo3.setText("Error getting Host-IP!");
            }
            buttonStartGame = new CustomButton("Spiel starten");
            buttonStartGame.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("DEBUG", "Start game");

                    /*Hier wird das Multiplayer Spiel gestartet (nur vom Host aus möglich)
                    screenManager.setScreen(new MultiplayerGameScreen(screenManager));
                     */

                    return super.touchDown(event, x, y, pointer, button);
                }
            });

        }else{
            labelInfo3.setText("Warten auf Spielstart...");
        }


        buttonExit = new HomeButton(screenManager);


        table.add(labelInfo1);
        table.row();
        table.add(labelInfo2);
        table.row();
        table.add(labelPlayerConnected);
        table.row();
        table.add(labelInfo3);
        table.row();
        if(host) {
            table.add(buttonStartGame).width(StyleHandler.getButtonWidth()).pad(10);
        }
        table.row();
        table.add(buttonExit).width(StyleHandler.getButtonWidth()).pad(10);
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
