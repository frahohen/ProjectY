package com.github.aconsultinggmbh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Alex on 29.05.2017.
 */
//Creating styles of GUI
    //Damit alle Buttons,labels, etc.. gleich aussehen

public class  StyleHandler {
    final static int buttonWidth= (int)(Gdx.graphics.getWidth()/3.f);
    final static int labelWidth= (int)(Gdx.graphics.getWidth()/18.f*5.f);

    public static TextButton.TextButtonStyle getButtonStyle(){
        //Buttonstyle
        TextureAtlas atlas = new TextureAtlas("button/button.pack");
        Skin skin = new Skin(atlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = getFont();
        textButtonStyle.fontColor = Color.WHITE;

        return textButtonStyle;
    }

    public static Label.LabelStyle getLabelStyle(){
        Label.LabelStyle labelStyle = new Label.LabelStyle( getFont(), Color.WHITE);
        return labelStyle;
    }

    public static BitmapFont getFont(){
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5.0f);
        return font;
    }
    public static int getButtonWidth(){
        return buttonWidth;
    }
    public static int getLabelWidth(){
        return labelWidth;
    }
}
