package com.github.aconsultinggmbh.gameobject;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Healthbar {
    final int height=75;            //Höhe des Balkens
    final int width=700;            //Breite des Balkens
    final int maxHP=100;            //Maximale Anzahl an Leben
    SpriteBatch batch;
    ProgressBar health;
    int currentHp=maxHP;            //Aktuelle Leben


   // Texture background = new Texture("HealthBarBackground.png");
    //Texture hpTexture = new Texture("HealthBar.png");
    ProgressBar.ProgressBarStyle barStyle;
    Skin skin;

    public Healthbar(){                                                                 //Erstellt den Lebensbalken

       barStyle = new ProgressBar.ProgressBarStyle();
        skin = new Skin();
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

       // TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(hpTexture));
       //Balken erzeugen
        health = new ProgressBar(0f, maxHP, 1f, false, barStyle);
        setStyle(Color.RED);
        health.setWidth(width);

        //Balken oben mittig platzieren
        health.setPosition(Gdx.graphics.getWidth()/2-width/2, Gdx.graphics.getHeight()-35-height);
        health.setValue(currentHp);             //Aktuelle Leben Anzeigen
        health.validate();

    }

    public void setStyle(Color color) {
        barStyle = new ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), skin.newDrawable("white",color));//erste farbe: "verlorene" HP, zweite Farbe: aktuelle Hp
        barStyle.knob.setMinHeight(height);
        barStyle.background.setMinHeight(height);
        barStyle.knobBefore = barStyle.knob;
        barStyle.knob.setMinWidth(0);
        health.setStyle(barStyle);
    }

    public void changeHP(int delta){            //Veringert aktuelle Hitpoints um den Wert delta, negatives delta "heilt" den Spieler
        currentHp=delta;
        if(currentHp>maxHP){
            currentHp=maxHP;     //Maximale HP-Anzahl kann nicht Ã¼berschritten werden

        }else{
            health.setValue(currentHp);

            //DO KILL THINGY HERE
            //Wird erst Implementiert
            //Punkte verteilen und den Spieler "despawnen"
        }


    }
    public ProgressBar getBar(){
        return health;
    }
    public int getCurrentHp(){
        return currentHp;
    }
    public int getMaxHP(){
        return maxHP;
    }


}