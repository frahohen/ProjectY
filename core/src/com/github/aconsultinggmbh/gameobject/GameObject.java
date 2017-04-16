package com.github.aconsultinggmbh.gameobject;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.aconsultinggmbh.map.CollisionMap;

import java.util.ArrayList;

public class GameObject {

    private Sprite gameObject;
    private CollisionObject collisionObject;
    private Vector3 transformVector;
    private ShapeRenderer shapeRenderer;
    private boolean isRender;

    private String name;

    private boolean isCollision;

    private boolean isRight;
    private boolean isLeft;
    private boolean isDown;
    private boolean isUp;

    private Rectangle intersection;

    public GameObject(String objectTexture, float x, float y, String name) {
        init(objectTexture,x,y,name);
    }

    public Rectangle getBounds(){
        return collisionObject.getBounds();
    }

    public float getX() {
        return collisionObject.getBounds().getX();
    }

    public void setX(float x) {
        this.collisionObject.getBounds().setX(x);
    }

    public float getY() {
        return collisionObject.getBounds().getY();
    }

    public void setY(float y) {
        this.collisionObject.getBounds().setY(y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isRender() {
        return isRender;
    }

    public void setRender(boolean render) {
        isRender = render;
    }

    private void init(String objectTexture, float x, float y, String name) {
        this.name = name;

        gameObject = new Sprite(new Texture(Gdx.files.internal(objectTexture)));
        collisionObject = new CollisionObject(x,y, gameObject.getWidth(), gameObject.getHeight());

        isRender = false;
        shapeRenderer = new ShapeRenderer();

        isCollision = false;

        isRight = false;
        isLeft = false;
        isDown = false;
        isUp = false;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera){
        transformVector = new Vector3(collisionObject.getBounds().getX(),collisionObject.getBounds().getY(),0);
        camera.project(transformVector);
        gameObject.setPosition(transformVector.x,transformVector.y);

        //Gdx.app.log("DEBUG", collisionObject.getBounds().getX()+";"+collisionObject.getBounds().getY());

        if(isRender) {
            batch.begin();
            gameObject.draw(batch);
            batch.end();
        }
    }

    public void showBounds(boolean isBounds, OrthographicCamera camera) {
        if(isBounds){
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(collisionObject.getBounds().getX(), collisionObject.getBounds().getY(), collisionObject.getBounds().getWidth(), collisionObject.getBounds().getHeight());
            shapeRenderer.end();
        }
    }

    public String collideWithObject(ArrayList<GameObject> gameObjects){
        String name = "";

        searchLoop:
        for(int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i).getBounds().overlaps(this.getBounds())) {
                name = gameObjects.get(i).getName();
                break searchLoop;
            }
        }

        return name;
    }

    public void collideWithMap(CollisionMap collisionMap){

        searchLoop:
        for(int i = 0; i < collisionMap.getMap().size(); i++) {
            if (collisionMap.getMap().get(i).overlaps(this.getBounds())) {

                intersection = new Rectangle();
                Intersector.intersectRectangles(this.getBounds(), collisionMap.getMap().get(i), intersection);

                if(intersection.x > this.getBounds().x) {
                    //Intersects with right side
                    isCollision = true;
                    isRight = true;
                    //Gdx.app.log("DEBUG", "Right");
                }

                if(intersection.y > this.getBounds().y){
                    //Intersects with top side
                    isCollision = true;
                    isUp = true;
                    //Gdx.app.log("DEBUG", "Top");
                }

                if(intersection.x + intersection.width < this.getBounds().x + this.getBounds().width){
                    //Intersects with left side
                    isCollision = true;
                    isLeft = true;
                    //Gdx.app.log("DEBUG", "Left");
                }

                if(intersection.y + intersection.height < this.getBounds().y + this.getBounds().height) {
                    //Intersects with bottom side
                    isCollision = true;
                    isDown = true;
                    //Gdx.app.log("DEBUG", "Bottom");
                }

                break searchLoop;
            }
        }
    }
}
