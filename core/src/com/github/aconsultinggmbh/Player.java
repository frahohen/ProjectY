package com.github.aconsultinggmbh;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 30.03.2017.
 */

public class Player {
    Sprite playerSprite;
    Vector2 position;
    Vector2 direction;

    public Player(int x, int y){//spawnposition
        position=new Vector2();
        direction=new Vector2();
        position.x=x;
        position.y=y;
        direction.x=0;
        direction.y=0;
        playerSprite=new Sprite( new Texture("PlayerTexture.png"));
    }

    public void update(float delta){
        position.add(direction);
    }
    public void draw(Batch batch){
        playerSprite.setRotation(direction.angle());
        playerSprite.setPosition(position.x,position.y);
        playerSprite.draw(batch);
    }


    //getter und setter methoden


    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }


    public Vector2 getPosition() {
        return position;
    }
}
