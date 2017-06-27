package com.github.aconsultinggmbh.screen;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.aconsultinggmbh.gameobject.Bullet;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.gameobject.Healthbar;
import com.github.aconsultinggmbh.gameobject.ItemInvulnerability;
import com.github.aconsultinggmbh.gameobject.Player;
import com.github.aconsultinggmbh.multiplayer.client.ClientProgram;
import com.github.aconsultinggmbh.multiplayer.server.ServerProgram;
import com.github.aconsultinggmbh.map.GameMap;
import com.github.aconsultinggmbh.networking.Client;
import com.github.aconsultinggmbh.networking.Server;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.CustomLabel;
import com.github.aconsultinggmbh.utils.GameTouchpad;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;


public class GameScreen implements Screen {

    private final ProjectY screenManager;
    private Vector3 calib;
    private boolean accelero;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private boolean menuIsActive=false;

    private boolean scoreboardIsActive=false;

    private GameTouchpad touchpad;

    private GameMap map;
    private Player player;
    private ArrayList<GameObject> items;
    private ArrayList<Bullet> bullets;
    private ArrayList<GameObject> enemies;

    private Healthbar hp;

    private float playerSpeed = 5.0f;
    private float scale = 6.0f;

   // private TextureAtlas atlas;
   // private Skin skin;
    private CustomButton buttonFire, buttonScore,buttonMenue;
   // private BitmapFont font;

    ScoreBoard sb;
    final GameScreen gameScreen=this;

    private CustomLabel labelScore;
    private CustomLabel labelRound;
    private int score;
    private int round;

    private Sound fireSound,announcer;

    private String collidedItemName;
    private String collidedEnemyName;

    private long start, end;
    private boolean flag = true;

    public GameScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    private void getPreferences(){
        // Einlesen der Settings Parameter für diese Klasse mit Default Werten
        Preferences settings = Gdx.app.getPreferences("ProjectY_settings");
        float x=settings.getFloat("x",0f); // 0f ist für wenn ein Fehler irgendwo Auftritt
        float y=settings.getFloat("y",0f);
        float z=settings.getFloat("z",0f);
        calib.set(x,y,z);
        accelero=settings.getBoolean("accelero",false);
    }


    private void soundsInit(){
        fireSound= Gdx.audio.newSound(Gdx.files.internal("Bulletshot.wav"));
        announcer= Gdx.audio.newSound(Gdx.files.internal("roundstart_announcer.wav"));
    }


    private void create(){

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        soundsInit();

        calib=new Vector3();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        collidedEnemyName = "";
        collidedItemName = "";

        //** GAME ** -START
        map = new GameMap("map.tmx",scale);

        player = new Player(
                "data/playerExample.png",
                map.getSpawnMap().getSpawnPoint(0).getX()-64,
                map.getSpawnMap().getSpawnPoint(0).getY()-64,
                "Player0"
        );

        player.setRender(true);
        hp= new Healthbar(player);
        enemies = new ArrayList<GameObject>();
        for(int i = 1; i < map.getSpawnMap().getSize(); i++){
            enemies.add(
                    new GameObject(
                            "data/playerExample.png",
                            map.getSpawnMap().getSpawnPoint(i).getX()-64,
                            map.getSpawnMap().getSpawnPoint(i).getY()-64,
                            "Enemy"+i
                    )
            );
        }

        bullets = new ArrayList<Bullet>();

        items = new ArrayList<GameObject>();
        for(int i = 0; i < 10; i++){
            int position = new Random().nextInt(map.getFloorMap().getSize());
            items.add(new ItemInvulnerability(
                    "data/item.png",
                    map.getFloorMap().getFloorPoint(position).getX()-64,
                    map.getFloorMap().getFloorPoint(position).getY()-64,
                    "Item"+i
            ));
        }

        // Camera set to player position
        camera.position.x = player.getX();
        camera.position.y = player.getY();
        //** GAME ** -END

        //** GUI ** - START
        touchpad = new GameTouchpad("data/touchBackground.png","data/touchKnob.png");
        touchpad.setRadius(10);
        touchpad.setBounds(15,15,200,200);
        touchpad.setPosition(Gdx.graphics.getWidth()- touchpad.getTouchpad().getWidth() -20, 20);

        stage = new Stage(new ScreenViewport(), batch);
/*
        // Button for shooting and Score
        atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);

        font = new BitmapFont();
        font.getData().setScale(5.0f);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
*/
        score = 0;
        //Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);
      //  labelScore = new Label("Score: "+score, labelStyle);
      //  labelScore.setWidth(400);
        labelScore=new CustomLabel("Score: "+score);
        labelScore.setPosition(Gdx.graphics.getWidth()- labelScore.getWidth() +60, Gdx.graphics.getHeight() -200);

        round = 0;
       // labelRound = new Label("Round: "+round, labelStyle);
       // labelRound.setWidth(400);
        labelRound=new CustomLabel("Round: "+round);
        labelRound.setPosition(40, Gdx.graphics.getHeight() -200);

        final GameScreen gameScreen=this;
        buttonMenue = new CustomButton("Menü");
        buttonMenue.setWidth(300);
        buttonMenue.setHeight(120);
        buttonMenue.setPosition(40, Gdx.graphics.getHeight()-110);
        buttonMenue.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //menü öffnen
                if(!menuIsActive){
                    menuIsActive=!menuIsActive;
                    SettingsInGame set =new SettingsInGame(stage,screenManager,gameScreen);
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        //ScoreboardButton

        buttonScore = new CustomButton("Score");
        buttonScore.setWidth(300);
        buttonScore.setHeight(120);
        buttonScore.setPosition(Gdx.graphics.getWidth()- buttonScore.getWidth()-40, Gdx.graphics.getHeight()-110);
        buttonScore.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                //Scoreboard anzeigen
                if(scoreboardIsActive == false){
                    scoreboardIsActive=!scoreboardIsActive;
                    sb =new ScoreBoard(stage,screenManager,gameScreen, score, player);
                }
                //Scoreboard ausblenden
                else if (scoreboardIsActive){
                    scoreboardIsActive=!scoreboardIsActive;
                    Gdx.app.log("DEBUG", "Kappa");

                    CustomLabel[] arr = sb.getLabelPlayerLabelScore();
                    for(CustomLabel l: arr){
                        l.remove();
                    }

                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonFire = new CustomButton("Fire");
        buttonFire.setWidth(300);
        buttonFire.setHeight(120);
        buttonFire.setPosition(20, 20);
        buttonFire.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Fire");
                bullets.add(
                        new Bullet(
                                "data/bullet.png",
                                player.getX()+64-16,
                                player.getY()+64-16,
                                "Bullet"
                        )
                );
                fireSound.play();
                bullets.get(bullets.size()-1).setDirectionX(touchpad.getWasPrecentX());
                bullets.get(bullets.size()-1).setDirectionY(touchpad.getWasPrecentY());

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(labelRound);
        stage.addActor(labelScore);
        stage.addActor(buttonFire);
        stage.addActor(buttonScore);
        stage.addActor(buttonMenue);
        stage.addActor(touchpad.getTouchpad());
        stage.addActor(hp.getBar());
        Gdx.input.setInputProcessor(stage);
        //** GUI ** - END

        //Server-Code verhindert, dass zurück zum Hauptmenü mit nachfolgendenden erneuten Spielstart funktioniert
        getPreferences();


        // Network - Start
        new Thread(new ServerProgram());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                new Thread(new ClientProgram());
            }
        }, 10);
        // Network - End
        announcer.play();
    }

    @Override
    public void show() {
    }
    int test=0;
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.100f, 0.314f, 0.314f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        touchpad.calcFuture();

        // Ist Accelero aktiv und aktiviert
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)&&accelero) {
            float accelZ = Gdx.input.getAccelerometerZ(); // aktuelle Position - der Ruhelage
            if(calib.z>0){
                accelZ -=calib.z;
                if(accelZ<(calib.z-10))accelZ=calib.z-10;
                accelZ*=10/(10-calib.z);
            } else if (calib.z < 0) {
                accelZ -=calib.z;
                if(accelZ>(10+calib.z))accelZ=10+calib.z;
                accelZ*=10/(10+calib.z);
            }
            float accelY = Gdx.input.getAccelerometerY();
            player.move(accelY, accelZ); // Bewegung des Spielers


        }else {
            player.move(touchpad.getTouchpad().getKnobPercentX() * playerSpeed, touchpad.getTouchpad().getKnobPercentY() * playerSpeed);
        }
        player.collideWithMap(map.getCollisionMap());

        camera.position.x = player.getX();
        camera.position.y = player.getY();

        map.render(camera);

        // Items
        collidedItemName = player.collideWithObject(items);
        for(int i = 0; i < items.size(); i++){
            if(collidedItemName.equals(items.get(i).getName())) {
                //Gdx.app.log("DEBUG",items.get(i).getName() + " touched");
                player.giveItemBehaviour(items, items.get(i));
                items.remove(i);
            }else{
                items.get(i).render(batch, camera);
                items.get(i).setRender(true);
            }
        }


        // Enemies
        for(int i = 0; i < enemies.size(); i++){
            if(collidedEnemyName.equals(enemies.get(i).getName())){
                enemies.remove(i);
            }else {
                enemies.get(i).render(batch, camera);
                enemies.get(i).setRender(true);
            }
        }

        if(enemies.size() == 0){
            //Scoreboard anzeigen
            if(flag){
                flag = false;
                start = System.currentTimeMillis();
                scoreboardIsActive=true;
                sb = new ScoreBoard(stage,screenManager,gameScreen, score, player);
            }
            end = System.currentTimeMillis();

            //5 sec delay fuer respawn
            if((end - start) >= 5000) {
                //Scoreboard ausblenden
                scoreboardIsActive = false;
                flag= true;
                CustomLabel[] arr = sb.getLabelPlayerLabelScore();
                for(CustomLabel l: arr){
                    l.remove();
                }

                respawn();
                collidedEnemyName = "";
                collidedItemName = "";
            }
        }

        // Bullets
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).collideWithMap(map.getCollisionMap());

            collidedEnemyName = bullets.get(i).collideWithObject(enemies);
            //Gdx.app.log("DEBUG","Enemy: " + collidedEnemyName);
            bullets.get(i).setEnemyName(collidedEnemyName);

            if(!bullets.get(i).getEnemyName().equals("")){
                //score+=10;
                score = bullets.get(i).checkScore(score);
                bullets.remove(i);
            } else if(bullets.get(i).isCollision()){
                bullets.remove(i);
            } else {
                bullets.get(i).setX(bullets.get(i).getX() + bullets.get(i).getDirectionX() * 20);
                bullets.get(i).setY(bullets.get(i).getY() + bullets.get(i).getDirectionY() * 20);
                bullets.get(i).render(batch, camera);
                bullets.get(i).setRender(true);
            }
        }

        // Labels
        labelScore.setText("Score: "+score);
        labelRound.setText("Round: "+round);

        if(player != null) {
            player.render(batch, camera);
            player.showBounds(false, camera);
        }

        map.showFloorMapBounds(false,camera,scale);

        // Draw stage for touchpad
        stage.act(delta);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

        fireSound.dispose();
        stage.dispose();
    }

    public void showScoreBoard(){
        sb =new ScoreBoard(stage,screenManager,gameScreen, score, player);
        long i = System.currentTimeMillis();
    }
    private void respawn(){

        announcer.play();

        round++;
        for(int i = 1; i < map.getSpawnMap().getSize(); i++){
            enemies.add(
                    new GameObject(
                            "data/playerExample.png",
                            map.getSpawnMap().getSpawnPoint(i).getX()-64,
                            map.getSpawnMap().getSpawnPoint(i).getY()-64,
                            "Enemy"+i
                    )
            );
        }

        player = new Player(
                "data/playerExample.png",
                map.getSpawnMap().getSpawnPoint(0).getX()-64,
                map.getSpawnMap().getSpawnPoint(0).getY()-64,
                "Player0"
        );
        player.setRender(true);
    }

    public void setMenuIsActive(boolean x){
        menuIsActive=x;
    }
    public void setAccelero(boolean x){
        accelero=x;
    }
    public void setCalib(float x, float y, float z){
        calib.x=x;
        calib.y=y;
        calib.z=z;
    }

}
