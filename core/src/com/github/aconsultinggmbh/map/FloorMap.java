package com.github.aconsultinggmbh.map;



import com.github.aconsultinggmbh.point.FloorPoint;

import java.util.ArrayList;

public class FloorMap {

    private ArrayList<FloorPoint> map;

    public FloorMap(){
        this.map = new ArrayList<FloorPoint>();
    }

    public int getSize(){
        return map.size();
    }

    public void add(float x, float y) {
        map.add(new FloorPoint(x,y));
    }

    public FloorPoint getFloorPoint(int index){
        return map.get(index);
    }
}
