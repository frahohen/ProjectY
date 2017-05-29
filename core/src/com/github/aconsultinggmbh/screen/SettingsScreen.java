package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class SettingsScreen implements Screen {

    private final ProjectY screenManager;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonBack, buttonCalib, buttonAccelero, buttonTMP2;
   // private BitmapFont font;

    public SettingsScreen(ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }

    private void create(){
        final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        stage = new Stage();

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
                settings.putBoolean("accelero",!settings.getBoolean("accelero",false));
                settings.flush();
                buttonAccelero.setText("Accelerometer:"+settings.getBoolean("accelero",false));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        // butttonTMP2 --> Platzhalter

        buttonTMP2 = new TextButton("Template 2", StyleHandler.getButtonStyle());
        buttonTMP2.pad(20);
        buttonTMP2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");

                return super.touchDown(event, x, y, pointer, button);
            }
        });


        buttonBack = new HomeButton(screenManager);
        buttonBack.pad(20);

        table.add(buttonCalib).width(600).pad(10);
        table.row();
        table.add(buttonAccelero).width(600).pad(10);
        table.row();
        table.add(buttonTMP2).width(600).pad(10);
        table.row();
        table.add(buttonBack).width(600).pad(10);
        //table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
