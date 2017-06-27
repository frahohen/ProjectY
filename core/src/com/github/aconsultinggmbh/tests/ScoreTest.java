package com.github.aconsultinggmbh.tests;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.aconsultinggmbh.gameobject.Bullet;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.gameobject.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ScoreTest extends GameTest {

    private ArrayList<GameObject> players;
    private String path;
    private Player player;
    private ArrayList<GameObject> bullets;
    private Bullet bullet;
    private int score;

    @Before
    public void setup() {

        FileHandle fh = Gdx.files.internal("android/assets/item.png");
        this.path = fh.file().getAbsolutePath().replace("core/","");

        players = new ArrayList<GameObject>();
        player = new Player(path, 0, 0, "Player");
        players.add(player);

        bullets = new ArrayList<GameObject>();
        bullet = new Bullet(path, 0, 0, "Bullet");
        bullets.add(bullet);

        score = 0;
    }

    @Test
    public void ScoreTest() {
        bullet = new Bullet(path, 0, 0, "Bullet");
        assertEquals(
                player.getName(),
                bullet.collideWithObject(players)
        );

        bullet.setEnemyName(player.getName());
        assertEquals(10, bullet.checkScore(score));
    }

    @Test
    public void initBulletTest() {
        String name ="TestBullet";
        Bullet b= new Bullet(path,0,0,name);
        assertEquals(0f,b.getDirectionX(),0);
        assertEquals(0f,b.getDirectionY(),0);
        assertEquals(name,b.getName());
        assertEquals("",b.getEnemyName());
    }

    @Test
    public void bulletEnemyNameTest() {
        Bullet b= new Bullet(path,0,0,"Bullet");
        String te="TestEnemy";
        b.setEnemyName(te);
        assertEquals(te,b.getEnemyName());
    }

    @Test
    public void bulletDirectionTest() {
        Bullet b= new Bullet(path,0,0,"Bullet");
        bullet.setDirectionX(20);
        assertEquals(20,bullet.getDirectionX(),0);
        bullet.setDirectionY(50);
        assertEquals(50,bullet.getDirectionY(),0);
    }
}