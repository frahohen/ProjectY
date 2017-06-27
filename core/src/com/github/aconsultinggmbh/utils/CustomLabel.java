package com.github.aconsultinggmbh.utils;

/**
 * Created by Alex on 12.06.2017.
 */
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CustomLabel extends Label {
    public CustomLabel(String text){
        super(text,StyleHandler.getLabelStyle());
        this.setWidth(StyleHandler.getLabelWidth());
    }
}
