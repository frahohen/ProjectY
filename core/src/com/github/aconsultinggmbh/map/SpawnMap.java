package com.github.aconsultinggmbh.map;


import com.github.aconsultinggmbh.point.MapPosition;

import java.util.ArrayList;

public class SpawnMap {

    private ArrayList<MapPosition> map;

    public SpawnMap(){
        this.map = new ArrayList<MapPosition>();
    }

    public void add(float x, float y) {
        map.add(new MapPosition(x,y));
    }

    public int getSize(){
        return map.size();
    }

    public MapPosition getSpawnPoint(int index){
        return map.get(index);
    }
}
