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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.aconsultinggmbh.gameobject.Bullet;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.gameobject.Healthbar;
import com.github.aconsultinggmbh.gameobject.ItemInvulnerability;
import com.github.aconsultinggmbh.gameobject.ItemType;
import com.github.aconsultinggmbh.gameobject.Player;
import com.github.aconsultinggmbh.map.GameMap;
import com.github.aconsultinggmbh.networking.Client;
import com.github.aconsultinggmbh.networking.Server;
import com.github.aconsultinggmbh.networking.message.MessageTag;
import com.github.aconsultinggmbh.point.MapPosition;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.CustomLabel;
import com.github.aconsultinggmbh.utils.GameTouchpad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MultiplayerGameScreen implements Screen {

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
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<GameObject> enemies;

    private Healthbar hp;

    private float playerSpeed = 5.0f;
    private float scale = 6.0f;

   // private TextureAtlas atlas;
   // private Skin skin;
    private CustomButton buttonFire, buttonScore,buttonMenue;
   // private BitmapFont font;

    ScoreBoard sb;
    final MultiplayerGameScreen multiplayerGameScreen =this;

    private CustomLabel labelScore;
    private CustomLabel labelRound;
    private int score;
    private int round;

    private Sound fireSound,announcer;

    private String collidedItemName;
    private String collidedEnemyName;

    private long start, end;
    private boolean flag = true;

    //Added
    //private boolean isMouse;
    //private int percentX = 0, percentY = 0;

    private boolean host;
    private Server server;
    private Client client;
    private boolean respawn = false;

    private boolean itemMode = false;
    private boolean fire = false;
    //added

    public MultiplayerGameScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    public MultiplayerGameScreen(ProjectY screenManager, boolean host,Client client,Server server) {
        this.screenManager = screenManager;
        this.host = host;
        this.client = client;
        this.server = server;
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
        enemies = new ArrayList<GameObject>();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        //** GAME ** -END

        //** SERVER ** - START
        if(host){
            try {
                // Set position of all Player and Items
                for(int i = 0; i < server.getThreadSize(); i++){
                    server.getPlayerAndPosition().put(
                            i+"",
                            new MapPosition(
                                    map.getSpawnMap().getSpawnPoint(i).getX(),
                                    map.getSpawnMap().getSpawnPoint(i).getY()
                            )
                    );
                    server.getPlayerAndHealth().put(i+"", 100);
                }

                server.updateClients(MessageTag.PLAYERPOSITION);
                // Wait until the Clients are updated
                Thread.sleep(1000);

                server.updateClients(MessageTag.PLAYERHEALTH);
                Thread.sleep(100);

                for(int i = 0; i < 10; i++){
                    int position = new Random().nextInt(map.getFloorMap().getSize());
                    server.getItemAndPosition().put(
                            i+":"+ ItemType.GODMODE,
                            new MapPosition(
                                    map.getFloorMap().getFloorPoint(position).getX(),
                                    map.getFloorMap().getFloorPoint(position).getY()
                            )
                    );
                }

                server.updateClients(MessageTag.ITEMPOSITION);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Set on Server every position of a Player
            spawn();
        }else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Set on Client every position of a Player
            spawn();
        }
        //** SERVER ** - END

        //** GUI ** - START
         hp=new Healthbar(player);
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

        final MultiplayerGameScreen multiplayerGameScreen =this;
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
                    SettingsInGame set =new SettingsInGame(stage,screenManager, multiplayerGameScreen);
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
                    sb =new ScoreBoard(stage,screenManager, multiplayerGameScreen, score, player);
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
                fire = true;
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

        if(fire){
            playerBullets.add(
                    new Bullet(
                            "data/bullet.png",
                            player.getX()+64-16,
                            player.getY()+64-16,
                            player.getName()+":"+playerBullets.size()
                    )
            );

            client.getClientSendHandler().updateClients(
                    MessageTag.PLAYERBULLETEXIST,
                    playerBullets.get(playerBullets.size()-1).getName()+"!"+"true"
            );
            // Waiting until message is send
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            client.getClientSendHandler().updateClients(
                    MessageTag.PLAYERBULLETPOSITION,
                    playerBullets.get(playerBullets.size()-1).getName()+"!"+
                            playerBullets.get(playerBullets.size()-1).getX()+":"+
                            playerBullets.get(playerBullets.size()-1).getY()
            );
            // Waiting until message is send
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            fireSound.play();
            playerBullets.get(playerBullets.size()-1).setDirectionX(touchpad.getWasPrecentX());
            playerBullets.get(playerBullets.size()-1).setDirectionY(touchpad.getWasPrecentY());

            fire = false;
        }

        if(player != null){
            if(player.getHealthPoints() > 0) {
                // Ist Accelero aktiv und aktiviert
                if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) && accelero) {
                    float accelZ = Gdx.input.getAccelerometerZ() - calib.z; // aktuelle Position - der Ruhelage
                    float accelY = Gdx.input.getAccelerometerY() - calib.y;
                    player.move(accelY, accelZ); // Bewegung des Spielers
                } else {
                    player.move(touchpad.getTouchpad().getKnobPercentX() * playerSpeed, touchpad.getTouchpad().getKnobPercentY() * playerSpeed);
                }
                player.collideWithMap(map.getCollisionMap());
            } else {
                player.setRender(false);
            }

            camera.position.x = player.getX()+64;
            camera.position.y = player.getY()+64;

            collidedItemName = player.collideWithObject(items);

            // Update Position
            client.getClientSendHandler().updateClients(MessageTag.PLAYERPOSITION, new MapPosition(player.getX()+64, player.getY()+64));

            // Update Health
            int health = client.getPlayerAndHealth().get(player.getName());
            //Gdx.app.log("DEBUG", health+"");
            if(player.getHealthPoints() != health){
                player.setHealthPoints(health);
                hp.update(health);
                Gdx.app.log("Debug",health+";"+host);
                if(health==0)respawn();
            }
        }



        map.render(camera);
        // Items
        for(int i = 0; i < items.size(); i++){
            if(collidedItemName.equals(items.get(i).getName())) {
                //Gdx.app.log("DEBUG",items.get(i).getName() + " touched");
                if(player != null){
                    player.giveItemBehaviour(items, items.get(i));

                    client.getClientSendHandler().updateClients(MessageTag.ITEMTAKEN, items.get(i).getName());
                    // Waiting until message is send
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if(player.isGodMode()){
                        itemMode = true;
                        hp.setStyle(Color.LIGHT_GRAY);
                        client.getClientSendHandler().updateClients(MessageTag.PLAYERGODMODE, true);
                    }
                }
                items.remove(i);
            }else{
                if(!player.isGodMode() && itemMode){
                    hp.setStyle(Color.RED);
                    client.getClientSendHandler().updateClients(MessageTag.PLAYERGODMODE, false);
                    itemMode = false;
                }

                // The player needs to remove the item a enemy has taken
                boolean taken = false;
                if(client.getItemAndTaken().containsKey(items.get(i).getName())){
                    taken = client.getItemAndTaken().get(items.get(i).getName());
                    Gdx.app.log("DEBUG", "taken: " + items.get(i).getName());
                }

                if(taken){
                    items.remove(i);
                }else{
                    items.get(i).render(batch, camera);
                    items.get(i).setRender(true);
                }
            }
        }

        // Enemy Bullets
        HashMap<String,MapPosition> bulletPositionHashMap = client.getBulletAndPosition();
        HashMap<String,Boolean> bulletExistHashMap = client.getBulletAndExist();
        for (Map.Entry<String, MapPosition> entry : bulletPositionHashMap.entrySet()) {
            String key = entry.getKey();
            MapPosition value = entry.getValue();

            // Check if the key is in playerBullets
            boolean containsKey = false;
            loopContainsKey:
            for(int i = 0; i < playerBullets.size(); i++){
                if(playerBullets.get(i).getName().equals(key)){
                    containsKey = true;
                    break loopContainsKey;
                }
            }

            if(!containsKey){
                // Check if the bullet of the enemy exist
                if(bulletExistHashMap.containsKey(key))
                {
                    if(bulletExistHashMap.get(key) == true){
                        // Check if bullet exists in the array
                        boolean containsBullet = false;
                        loopContainsBullet:
                        for(int i = 0; i < enemyBullets.size(); i++){
                            if(enemyBullets.get(i).getName().equals(key)){
                                containsBullet = true;
                                break loopContainsBullet;
                            }
                        }

                        // If not create it
                        if(!containsBullet){
                            enemyBullets.add(
                                    new Bullet(
                                            "data/bullet.png",
                                            value.getX()+64-16,
                                            value.getY()+64-16,
                                            key
                                    )
                            );
                        }
                    }

                    if(bulletExistHashMap.get(key) == false){
                        loopRemoveBullet:
                        for(int i = 0; i < enemyBullets.size(); i++){
                            if(enemyBullets.get(i).getName().equals(key)){
                                enemyBullets.remove(i);
                                break loopRemoveBullet;
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).setX(bulletPositionHashMap.get(enemyBullets.get(i).getName()).getX());
            enemyBullets.get(i).setY(bulletPositionHashMap.get(enemyBullets.get(i).getName()).getY());
            enemyBullets.get(i).render(batch, camera);
            enemyBullets.get(i).setRender(true);
        }

        if(enemies.size() == 0){
            //Scoreboard anzeigen
            /*
            if(flag){
                flag = false;
                start = System.currentTimeMillis();
                scoreboardIsActive=true;
                sb = new ScoreBoard(stage,screenManager, multiplayerGameScreen, score, player);
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
 */
                respawn();

                collidedEnemyName = "";
                collidedItemName = "";
            /*
            }
            */
        } else {
            for(int i = 0; i < enemies.size(); i++) {
                if (collidedEnemyName.equals(enemies.get(i).getName())) {
                    // Get health of player and only delete him if his health is 0
                    int health = (Integer) client.getPlayerAndHealth().get(enemies.get(i).getName());
                    // Check if the enemy has godMode
                    boolean godMode = false;
                    if (client.getPlayerAndGodMode().containsKey(enemies.get(i).getName())) {
                        godMode = client.getPlayerAndGodMode().get(enemies.get(i).getName());
                    }

                    if (!godMode) {
                        health = health - 25;
                    }

                    client.getClientSendHandler().updateClients(MessageTag.PLAYERHEALTH, enemies.get(i).getName() + ":" + health);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    //Gdx.app.log("DEBUG", enemies.get(i).getName()+":"+health);
                    enemies.get(i).render(batch, camera);
                    enemies.get(i).setRender(true);

                    if (health <= 0) {
                        player.setScore(player.getScore() + 10);
                        score = player.getScore();
                        enemies.remove(i);
                    } else {
                        collidedEnemyName = "";
                    }
                } else {
                    // Update Enemy Status
                    int health = client.getPlayerAndHealth().get(enemies.get(i).getName());

                    // The player needs to remove the enemy that is killed by another enemy
                    if (health <= 0) {
                        enemies.remove(i);
                    } else {
                        enemies.get(i).render(batch, camera);
                        enemies.get(i).setRender(true);

                        if (client.getPlayerAndPosition() != null && player != null) {
                            // Update Position of other Players
                            MapPosition mapposition = (MapPosition) client.getPlayerAndPosition().get(enemies.get(i).getName());

                            if (mapposition != null) {
                                //Gdx.app.log("DEBUG", mapposition.getX() + ":" + mapposition.getY());
                                enemies.get(i).setX(mapposition.getX() - 64);
                                enemies.get(i).setY(mapposition.getY() - 64);
                            }
                        }
                    }
                }
            }
        }

        // Player Bullets
        for(int i = 0; i < playerBullets.size(); i++){
            playerBullets.get(i).collideWithMap(map.getCollisionMap());

            collidedEnemyName = playerBullets.get(i).collideWithObject(enemies);
            //Gdx.app.log("DEBUG","Enemy: " + collidedEnemyName);
            playerBullets.get(i).setEnemyName(collidedEnemyName);

            if(!playerBullets.get(i).getEnemyName().equals("")){

                client.getClientSendHandler().updateClients(
                        MessageTag.PLAYERBULLETEXIST,
                        playerBullets.get(i).getName()+"!"+"false"
                );
                // Waiting until message is send
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                playerBullets.remove(i);
            } else if(playerBullets.get(i).isCollision()){

                client.getClientSendHandler().updateClients(
                        MessageTag.PLAYERBULLETEXIST,
                        playerBullets.get(i).getName()+"!"+"false"
                );
                // Waiting until message is send
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                playerBullets.remove(i);
            } else {

                playerBullets.get(i).setX(playerBullets.get(i).getX() + playerBullets.get(i).getDirectionX() * 20);
                playerBullets.get(i).setY(playerBullets.get(i).getY() + playerBullets.get(i).getDirectionY() * 20);

                client.getClientSendHandler().updateClients(
                        MessageTag.PLAYERBULLETPOSITION,
                        playerBullets.get(i).getName()+"!"+
                                playerBullets.get(i).getX()+":"+
                                playerBullets.get(i).getY()
                );

                playerBullets.get(i).render(batch, camera);
                playerBullets.get(i).setRender(true);
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
        sb =new ScoreBoard(stage,screenManager, multiplayerGameScreen, score, player);
        long i = System.currentTimeMillis();
    }

    private void spawn(){
        for(int i = 0; i < client.getPlayerAndPosition().size(); i++){
            if(client.getId() == i){
                player = new Player(
                        "data/playerExample.png",
                        ((MapPosition) client.getPlayerAndPosition().get(i+"")).getX()-64,
                        ((MapPosition) client.getPlayerAndPosition().get(i+"")).getY()-64,
                        i+""
                );
                // Render player
                client.getClientSendHandler().updateClients(MessageTag.PLAYERGODMODE, false);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                player.setRender(true);

                // Camera set to player position
                camera.position.x = player.getX()+64;
                camera.position.y = player.getY()+64;
            } else {
                enemies.add(
                        new GameObject(
                                "data/playerExample.png",
                                ((MapPosition) client.getPlayerAndPosition().get(i+"")).getX()-64,
                                ((MapPosition) client.getPlayerAndPosition().get(i+"")).getY()-64,
                                i+""
                        )
                );
                client.getClientSendHandler().updateClients(MessageTag.PLAYERHEALTH, enemies.get(enemies.size()-1).getName()+":"+100);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        items = new ArrayList<GameObject>();
        HashMap<String,MapPosition> hashmap = client.getItemAndPosition();
        for (Map.Entry<String, MapPosition> entry : hashmap.entrySet()) {
            String key = entry.getKey();
            MapPosition value = entry.getValue();

            String[] stringArray = key.split(":");

            if(stringArray[1].equals(ItemType.GODMODE)){
                items.add(new ItemInvulnerability(
                        "data/item.png",
                        value.getX()-64,
                        value.getY()-64,
                        stringArray[0]+":"+stringArray[1]
                ));
            }

            // If another type of cheat is needed then add it hear with an if clause
        }
    }


    private void respawn(){

        int alive = 0;
        for (Map.Entry<String, Integer> entry : client.getPlayerAndHealth().entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if(value > 0){
                alive++;
            }
        }

        if(alive == 1 && respawn == false){
            respawn = true;
        }

        if(respawn){
            round++;
            Gdx.app.log("Debug","Respawn:"+host);
            announcer.play();
            if(host){
                server.getItemAndTaken().clear();
                server.getPlayerAndGodMode().clear();
                server.getPlayerAndHealth().clear();
                server.getPlayerAndPosition().clear();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for(int i = 0; i < server.getThreadSize(); i++){

                    server.getPlayerAndPosition().put(
                            i+"",
                            new MapPosition(
                                    map.getSpawnMap().getSpawnPoint(i).getX(),
                                    map.getSpawnMap().getSpawnPoint(i).getY()
                            )
                    );

                    server.getPlayerAndHealth().put(i+"", 100);
                }

                try{
                    server.updateClients(MessageTag.PLAYERPOSITION);
                    // Wait until the Clients are updated
                    Thread.sleep(1000);

                    server.updateClients(MessageTag.PLAYERHEALTH);
                    Thread.sleep(1000);

                    server.updateClients(MessageTag.ITEMTAKEN);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                spawn();
            }else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                spawn();
            }
            respawn = false;
        }
        hp.setPlayer(player);

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
