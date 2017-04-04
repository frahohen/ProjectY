package com.github.aconsultinggmbh.map;


import java.util.ArrayList;

public class SpawnMap {

    private ArrayList<SpawnPoint> map;

    public SpawnMap(){
        this.map = new ArrayList<SpawnPoint>();
    }

    public void add(float x, float y) {
        map.add(new SpawnPoint(x,y));
    }

    public SpawnPoint getSpawnPoint(int index){
        return map.get(index);
    }
}
