package com.github.aconsultinggmbh.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class GameMap {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer layer;

    private ShapeRenderer shapeRenderer;

    private float width;
    private float height;

    private float tileCountX;
    private float tileCountY;
    private float tileWidth;
    private float tileHeight;

    private CollisionMap collisionMap;
    private SpawnMap spawnMap;

    public GameMap(String pathToMap, float scale){
        init(pathToMap, scale);
        load(scale);
    }

    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public SpawnMap getSpawnMap() {
        return spawnMap;
    }

    public void setSpawnMap(SpawnMap spawnMap) {
        this.spawnMap = spawnMap;
    }

    public CollisionMap getCollisionMap() {
        return collisionMap;
    }

    public void setCollisionMap(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private void init(String pathToMap, float scale) {
        tiledMap = new TmxMapLoader().load(pathToMap);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scale);

        MapProperties properties = tiledMap.getProperties();

        this.tileCountX = (float) properties.get("width", Integer.class);
        this.tileCountY = (float) properties.get("height", Integer.class);
        this.tileWidth = (float) properties.get("tilewidth", Integer.class);
        this.tileHeight = (float) properties.get("tileheight", Integer.class);

        this.width = tileCountX * tileWidth * scale;
        this.height = tileCountY * tileHeight * scale;

        shapeRenderer = new ShapeRenderer();
    }

    private void load(float scale){
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("map");

        collisionMap = new CollisionMap();
        spawnMap = new SpawnMap();

        for(int x = 0; x < layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);

                boolean isSpawn = cell.getTile().getProperties().get("isSpawn", Boolean.class);
                boolean isCollision = cell.getTile().getProperties().get("isCollision", Boolean.class);

                if(isSpawn){
                    spawnMap.add(
                            (x * tileWidth * scale)+ (tileWidth * scale)/2,
                            (y * tileHeight * scale) + (tileHeight * scale)/2);
                }

                if(isCollision){
                    collisionMap.add(new Rectangle(
                            x* tileWidth * scale,
                            y* tileHeight * scale,
                            tileWidth * scale,
                            tileHeight * scale));
                }
            }
        }
    }

    public void showBounds(boolean isBounds, OrthographicCamera camera) {
        if(isBounds){
            shapeRenderer.setProjectionMatrix(camera.combined);
            for(int i = 0; i < collisionMap.getMap().size(); i++) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.rect(collisionMap.getMap().get(i).getX(), collisionMap.getMap().get(i).getY(), collisionMap.getMap().get(i).getWidth(), collisionMap.getMap().get(i).getHeight());
                shapeRenderer.end();
            }
        }
    }
}
