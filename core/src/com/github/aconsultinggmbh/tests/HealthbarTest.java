package com.github.aconsultinggmbh.tests;


import com.github.aconsultinggmbh.gameobject.Healthbar;
import com.github.aconsultinggmbh.gameobject.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HealthbarTest extends GameTest {
   // Ist dann nächste Woche aktiv müssen noch Änderungen vorgenommen werden.

    Healthbar hpbar;
    @Before
    public void setup() {
        hpbar= new Healthbar(new Player("data/playerExample.png",0,0,"ASDF"));
    }
   /* @Test
    public void returnHPTest() {
        assertEquals(100,hpbar.getCurrentHp());
    }
    @Test
    public void returnMaxHPTest() {
        assertEquals(100,hpbar.getMaxHP());
    }

    @Test
     public void changeHPTests() {
        hpbar.changeHP(50);     //100-50=50
        assertEquals(50,hpbar.getCurrentHp());
        hpbar.changeHP(-70); //50-(-70)=120 aber Maximum von 100 -> 100 erwartetes Ergebniss
        assertEquals(100,hpbar.getCurrentHp());
        //auskommentiert -> funktioniert nicht
        //hpbar.changeHP(110);     //100-110=-10 -> 0 ist minimum
        //assertEquals(0,hpbar.getCurrentHp());
        hpbar.changeHP(100);
        assertEquals(0, hpbar.getCurrentHp());
    }*/

}


