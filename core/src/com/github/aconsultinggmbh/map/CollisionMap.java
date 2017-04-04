package com.github.aconsultinggmbh.map;


import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class CollisionMap {

    private ArrayList<Rectangle> map;

    public CollisionMap(){
        this.map = new ArrayList<Rectangle>();
    }

    public void add(Rectangle rectangle) {
        map.add(rectangle);
    }

    public ArrayList<Rectangle> getMap() {
        return map;
    }

    public void setMap(ArrayList<Rectangle> map) {
        this.map = map;
    }
}
