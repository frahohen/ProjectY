package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
    private TextButton buttonExit, buttonStartGame;
    private BitmapFont font;
    private Label labelInfo1,labelInfo2,labelInfo3,labelPlayerConnected;

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

        font = new BitmapFont();
        font.getData().setScale(5.0f);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);
        labelInfo1 = new Label("Willkommen in der Lobby!", labelStyle);
        labelInfo1.setWidth(500);
        labelInfo2 = new Label("Warten auf Spieler...", labelStyle);
        labelInfo2.setWidth(500);
        labelInfo3 = new Label("", labelStyle);
        labelInfo3.setWidth(500);
        //Fraglich?? wollen wir haben, dass er anzeigt wieviele spieler gerade in der Lobby sind?
        labelPlayerConnected  = new Label("Spieler verbunden:---", labelStyle);
        labelInfo3.setWidth(500);
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
            buttonStartGame = new TextButton("Spiel starten", textButtonStyle);
            buttonStartGame.pad(20);
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



        buttonExit = new TextButton("Hauptmenü", textButtonStyle);
        buttonExit.pad(20);
        buttonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Hauptmenü öffnen");
                /*          ------------
                       HIER DIE VERBINDUNG ABBRECHEN
                            ---------------  */
                screenManager.setScreen(new MainMenuScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        table.add(labelInfo1);
        table.row();
        table.add(labelInfo2);
        table.row();
        table.add(labelPlayerConnected);
        table.row();
        table.add(labelInfo3);
        table.row();
        if(host) {
            table.add(buttonStartGame).width(600).pad(10);
        }
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
