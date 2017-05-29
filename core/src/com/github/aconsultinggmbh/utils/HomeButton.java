package com.github.aconsultinggmbh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.aconsultinggmbh.screen.MainMenuScreen;
import com.github.aconsultinggmbh.screen.ProjectY;

/**
 * Created by Alex on 29.05.2017.
 */

public class HomeButton extends TextButton {

    //Zurück zum Hauptmenü Button
    public HomeButton(final ProjectY screenManager){
        super("Hauptmenü",StyleHandler.getButtonStyle());
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Hauptmenü öffnen");
                screenManager.setScreen(new MainMenuScreen(screenManager));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
