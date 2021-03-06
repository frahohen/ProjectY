package com.github.aconsultinggmbh.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.gameobject.Item;
import com.github.aconsultinggmbh.gameobject.ItemInvulnerability;
import com.github.aconsultinggmbh.gameobject.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ItemTest extends GameTest {
    private Item item;
    private ItemInvulnerability itemInvulnerability;
    private ArrayList<GameObject> items;
    private ArrayList<GameObject> players;
    private String path;
    private Player player;

    @Before
    public void setup() {

        FileHandle fh = Gdx.files.internal("android/assets/item.png");
        this.path = fh.file().getAbsolutePath().replace("core/","");

        item = new Item(path, 0,0, "Item");
        itemInvulnerability = new ItemInvulnerability(path, 0,0, "Item");

        items = new ArrayList<GameObject>();
        players = new ArrayList<GameObject>();

        items.add(item);
        items.add(itemInvulnerability);

        player = new Player(path, 0,0, "Player");
        players.add(player);
    }

   @Test
    public void testCollideWithItem(){ //item with item
        Item newItem = new Item(path, 0,0, "NewItem");
        assertEquals(
                item.getName(),
                newItem.collideWithObject(items)
        );
        assertEquals(
                itemInvulnerability.getName(),
                newItem.collideWithObject(items)
        );
    }

    @Test
    public void testCollideWithPlayer (){ //player mit item
       Item newItem = new Item(path, 0,0, "NewItem");
        assertEquals(
                player.getName(),
                newItem.collideWithObject(players)
        );
    }

    //Gegner mit item ??
    //FloorMap und items?



}
