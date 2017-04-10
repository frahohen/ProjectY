package com.github.aconsultinggmbh.gameobject;


import com.badlogic.gdx.math.Rectangle;

public class CollisionObject {

    private Rectangle rectangle;

    public CollisionObject(float x, float y, float width, float height) {
        this.rectangle = new Rectangle(x,y,width,height);
    }

    public Rectangle getBounds() {
        return rectangle;
    }
}
