package com.github.aconsultinggmbh.gameobject;

public class Bullet extends GameObject {
    private float directionX;
    private float directionY;
    private String EnemyName;


    public Bullet(String objectTexture, float x, float y, String name) {
        super(objectTexture, x, y, name);

        this.directionX = 0;
        this.directionY = 0;
        this.EnemyName = "";

    }

    public float getDirectionX() {
        return directionX;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }

    public String getEnemyName() {
        return EnemyName;
    }

    public void setEnemyName(String enemyName) {
        EnemyName = enemyName;
    }

    public int checkScore(int score) {
        if (!this.getEnemyName().equals("")) {
            score += 10;
        }
        return score;
    }
}
