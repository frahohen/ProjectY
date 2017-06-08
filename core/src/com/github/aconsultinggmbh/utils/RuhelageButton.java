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

public class RuhelageButton extends TextButton {

    final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");

    //Ruhelage setzen - Button
    public RuhelageButton(final GameScreen gameScreen){

        super("Ruhelage setzen",StyleHandler.getButtonStyle());

        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,
                                     float x,
                                     float y,
                                     int pointer,
                                     int button) {
                float accelZ = Gdx.input.getAccelerometerZ(); // Ruhelage gesetz (Aktuelle Position)
                float accelX = Gdx.input.getAccelerometerX();
                float accelY = Gdx.input.getAccelerometerY();

                settings.putFloat("x",accelX); // Speicherung der Ruhe in Settings  Parameter können immer verwendet werden
                settings.putFloat("y",accelY);
                settings.putFloat("z",accelZ);
                settings.flush(); // jetzt speichern
                gameScreen.setCalib(accelX,accelY,accelZ);
                return super.touchDown(event, x, y, pointer, button);
            }
        }
        )

        ;
    }
    public RuhelageButton(){

        super("Ruhelage setzen",StyleHandler.getButtonStyle());

        this.addListener(new InputListener(){
                             @Override
                             public boolean touchDown(InputEvent event,
                                                      float x,
                                                      float y,
                                                      int pointer,
                                                      int button) {
                                 float accelZ = Gdx.input.getAccelerometerZ(); // Ruhelage gesetz (Aktuelle Position)
                                 float accelX = Gdx.input.getAccelerometerX();
                                 float accelY = Gdx.input.getAccelerometerY();

                                 settings.putFloat("x",accelX); // Speicherung der Ruhe in Settings  Parameter können immer verwendet werden
                                 settings.putFloat("y",accelY);
                                 settings.putFloat("z",accelZ);
                                 settings.flush(); // jetzt speichern

                                 return super.touchDown(event, x, y, pointer, button);
                             }
                         }
        )

        ;
    }
}
