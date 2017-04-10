package com.github.aconsultinggmbh.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.aconsultinggmbh.gameobject.Bullet;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.map.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    private final ProjectY screenManager;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private GameMap map;

    private ArrayList<Bullet> bullets;
    private ArrayList<GameObject> items;
    private ArrayList<GameObject> enemies;

    private float scale = 6.0f;

    private TextureAtlas atlas;
    private Skin skin;
    private TextButton buttonFire;
    private BitmapFont font;

    private Label labelScore;
    private Label labelRound;
    private int score;
    private int round;

    private String collidedEnemyName;
    private String collidedItemName;

    public GameScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    private void create(){
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        collidedEnemyName = "";
        collidedItemName = "";

        //** GAME ** -START
        map = new GameMap("map.tmx",scale);

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

        items = new ArrayList<GameObject>();
        for(int i = 0; i < 10; i++){
            int position = new Random().nextInt(map.getFloorMap().getSize());
            items.add(new GameObject(
                    "data/item.png",
                    map.getFloorMap().getFloorPoint(position).getX()-64,
                    map.getFloorMap().getFloorPoint(position).getY()-64,
                    "Item"+i
            ));
        }

        bullets = new ArrayList<Bullet>();

        stage = new Stage(new ScreenViewport(), batch);

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

        score = 0;
        Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);
        labelScore = new Label("Score: "+score, labelStyle);
        labelScore.setWidth(400);
        labelScore.setPosition(Gdx.graphics.getWidth()- labelScore.getWidth()-40, Gdx.graphics.getHeight() - labelScore.getHeight()-20);

        round = 0;
        labelRound = new Label("Round: "+round, labelStyle);
        labelRound.setWidth(400);
        labelRound.setPosition(40, Gdx.graphics.getHeight() - labelScore.getHeight()-20);

        buttonFire = new TextButton("Fire", textButtonStyle);
        buttonFire.setWidth(300);
        buttonFire.setHeight(120);
        buttonFire.setPosition(20, 20);
        buttonFire.pad(20);
        buttonFire.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Fire");

                bullets.add(
                        new Bullet(
                                "data/bullet.png",
                                0,
                                0,
                                "Bullet")
                );
                bullets.get(bullets.size()-1).setDirectionX(1);
                bullets.get(bullets.size()-1).setDirectionY(0);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(labelRound);
        stage.addActor(labelScore);
        stage.addActor(buttonFire);
        Gdx.input.setInputProcessor(stage);
        //** GUI ** - END
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.100f, 0.314f, 0.314f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        camera.position.x = 0;
        camera.position.y = 0;

        map.render(camera);

        // Items
        //collidedItemName = player.collideWithObject(items);
        for(int i = 0; i < items.size(); i++){
            if(collidedItemName.equals(items.get(i).getName())) {
                //Gdx.app.log("DEBUG",items.get(i).getName() + " touched");
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
            respawn();
            collidedEnemyName = "";
            collidedItemName = "";
        }

        // Bullets
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).collideWithMap(map.getCollisionMap());

            collidedEnemyName = bullets.get(i).collideWithObject(enemies);
            //Gdx.app.log("DEBUG","Enemy: " + collidedEnemyName);
            bullets.get(i).setEnemyName(collidedEnemyName);

            if(!bullets.get(i).getEnemyName().equals("")){
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

        // Draw stage for touchpad
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

    }

    private void respawn(){
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
    }
}
