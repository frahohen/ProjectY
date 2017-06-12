package com.github.aconsultinggmbh.utils;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Alex on 12.06.2017.
 */

public class CustomButton extends TextButton {
    public CustomButton(String text){
        super(text,StyleHandler.getButtonStyle());
        this.pad(20);
    }
}
