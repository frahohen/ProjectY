package com.github.aconsultinggmbh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.aconsultinggmbh.screen.GameScreen;

/**
 * Created by maile on 08.06.17.
 */

public class AcceleroButton extends TextButton {

    final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");

    public AcceleroButton() {

        super("Init", StyleHandler.getButtonStyle());
        this.setText("Accelerometer: "+settings.getBoolean("accelero",false));
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,
                                     float x,
                                     float y,
                                     int pointer,
                                     int button) {
                setAccelero();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    public AcceleroButton(final GameScreen gameScreen) {

        super("Init", StyleHandler.getButtonStyle());
        this.setText("Accelerometer: "+settings.getBoolean("accelero",false));
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event,
                                     float x,
                                     float y,
                                     int pointer,
                                     int button) {

                setAccelero();
                gameScreen.setAccelero(settings.getBoolean("accelero",false));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    private void setAccelero(){

        Gdx.app.log("DEBUG", "Pressed");
        settings.putBoolean("accelero",!settings.getBoolean("accelero",false));
        settings.flush();
        setText("Accelerometer:"+settings.getBoolean("accelero",false));
    }
}