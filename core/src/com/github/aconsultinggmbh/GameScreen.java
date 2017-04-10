package com.github.aconsultinggmbh;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.aconsultinggmbh.gameobject.Bullet;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.map.GameMap;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final ProjectY screenManager;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private GameMap map;

    private ArrayList<Bullet> bullets;
    private ArrayList<GameObject> enemies;

    private float scale = 6.0f;

    private String collidedEnemyName;

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

        bullets = new ArrayList<Bullet>();

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
            collidedEnemyName = "";
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
