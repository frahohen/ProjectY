package com.github.aconsultinggmbh.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class GameTouchpad {

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;

    private float wasPrecentX;
    private float wasPrecentY;

    public GameTouchpad(String backgroundTexture, String knobTexture) {
        init(backgroundTexture, knobTexture);
    }

    public float getWasPrecentX() {
        return wasPrecentX;
    }

    public void setWasPrecentX(float wasPrecentX) {
        this.wasPrecentX = wasPrecentX;
    }

    public float getWasPrecentY() {
        return wasPrecentY;
    }

    public void setWasPrecentY(float wasPrecentY) {
        this.wasPrecentY = wasPrecentY;
    }

    public void setPosition(float x, float y) {
        touchpad.setPosition(x,y);
    }

    public void setBounds(float x, float y, float width, float height) {
        touchpad.setBounds(x, y, width, height);
    }

    public void setRadius(float radius) {
        touchpad = new Touchpad(radius, touchpadStyle);
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    private void init(String backgroundTexture, String knobTexture){
        wasPrecentX = 1;
        wasPrecentY = 0;

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(backgroundTexture));
        touchpadSkin.add("touchKnob", new Texture(knobTexture));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
    }

    public void calcFuture(){

        //Gdx.app.log("DEBUG","X:"+wasPrecentX+";Y:"+wasPrecentY);

        if(touchpad.getKnobPercentX() != 0) {
            wasPrecentX = touchpad.getKnobPercentX();
        }
        if(touchpad.getKnobPercentY() != 0) {
            wasPrecentY = touchpad.getKnobPercentY();
        }
    }
}
