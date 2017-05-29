package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.aconsultinggmbh.utils.HomeButton;
import com.github.aconsultinggmbh.utils.StyleHandler;

/**
 * Created by maile on 04.05.17.
 */

public class SettingsInGame {
    private final ProjectY screenManager;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonBack, buttonCalib, buttonAccelero, buttonPlay;
  //  private BitmapFont font;
    private GameScreen gameScreen;

    public SettingsInGame(Stage stage, ProjectY sm, final GameScreen gameScreen){
        this.screenManager = sm;

        this.gameScreen=gameScreen;

        final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");

        atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

       /* font = new BitmapFont();
        font.getData().setScale(5.0f);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;*/

        // Spiel fortsetzen

        buttonPlay = new TextButton("Spiel fortsetzen", StyleHandler.getButtonStyle());
        buttonPlay.pad(20);
        buttonPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");
                destroyButtons();
                //game.on
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        buttonCalib = new TextButton("Ruhelage setzten", StyleHandler.getButtonStyle());
        buttonCalib.pad(20);
        buttonCalib.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Ruhelage bestimmt");
                if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)){ // Accelero eingelesen
                    float accelZ = Gdx.input.getAccelerometerZ(); // Ruhelage gesetz (Aktuelle Position)
                    float accelX = Gdx.input.getAccelerometerX();
                    float accelY = Gdx.input.getAccelerometerY();

                    settings.putFloat("x",accelX); // Speicherung der Ruhe in Settings  Parameter können immer verwendet werden
                    settings.putFloat("y",accelY);
                    settings.putFloat("z",accelZ);
                    settings.flush(); // jetzt speichern
                    gameScreen.setCalib(accelX,accelY,accelZ);

                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        buttonAccelero = new TextButton("Accelerometer:"+settings.getBoolean("accelero",false), StyleHandler.getButtonStyle());


        buttonAccelero.pad(20);

        //Ob Accelero aktiv oder nicht ist und wieder in die Settings speichern
        // Settings bleiben dauerhaft
        buttonAccelero.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");

                gameScreen.setAccelero(!settings.getBoolean("accelero",false));
                settings.putBoolean("accelero",!settings.getBoolean("accelero",false));
                settings.flush();

                buttonAccelero.setText("Accelerometer:"+settings.getBoolean("accelero",false));
                return super.touchDown(event, x, y, pointer, button);
            }
        });



        // zurück zum Hauptmenü

        buttonBack = new HomeButton(screenManager);
        buttonBack.pad(20);

        table.add(buttonCalib).width(600).pad(10);
        table.row();
        table.add(buttonAccelero).width(600).pad(10);
        table.row();
        table.add(buttonPlay).width(600).pad(10);
        table.row();
        table.add(buttonBack).width(600).pad(10);
        //table.debug();

        stage.addActor(table);
    }

    void destroyButtons(){
        table.remove();
        gameScreen.setMenuIsActive(false);

    }




}
