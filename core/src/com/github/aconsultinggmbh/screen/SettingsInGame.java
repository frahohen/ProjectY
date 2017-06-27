package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.aconsultinggmbh.utils.AcceleroButton;
import com.github.aconsultinggmbh.utils.CustomButton;
import com.github.aconsultinggmbh.utils.HomeButton;
import com.github.aconsultinggmbh.utils.RuhelageButton;
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
    private CustomButton buttonBack, buttonCalib, buttonAccelero, buttonPlay;
    private GameScreen gameScreen;
    private MultiplayerGameScreen multiplayerGameScreen;

    public SettingsInGame(Stage stage, ProjectY sm, final GameScreen gameScreen){
        this.screenManager = sm;
        this.gameScreen=gameScreen;

        init(stage, sm);
    }

    public SettingsInGame(Stage stage, ProjectY sm, final MultiplayerGameScreen gameScreen){
        this.screenManager = sm;
        this.multiplayerGameScreen=gameScreen;

        init(stage, sm);
    }

    public void init(Stage stage, ProjectY sm){
        atlas = new TextureAtlas("button/button.pack");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Spiel fortsetzen

        buttonPlay = new CustomButton("Spiel fortsetzen");
        buttonPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Pressed");
                destroyButtons();
                //game.on
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        buttonCalib =new RuhelageButton(gameScreen);

        buttonAccelero=new AcceleroButton(gameScreen);
        // zurück zum Hauptmenü
        buttonBack = new HomeButton(screenManager);

        table.add(buttonCalib).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonAccelero).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonPlay).width(StyleHandler.getButtonWidth()).pad(10);
        table.row();
        table.add(buttonBack).width(StyleHandler.getButtonWidth()).pad(10);
        //table.debug();

        stage.addActor(table);
    }

    void destroyButtons(){
        table.remove();
        if(gameScreen == null){
            multiplayerGameScreen.setMenuIsActive(false);
        }else {
            gameScreen.setMenuIsActive(false);
        }

    }




}
