package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.aconsultinggmbh.gameobject.Player;
import com.github.aconsultinggmbh.utils.CustomLabel;
import com.github.aconsultinggmbh.utils.StyleHandler;


/**
 * Created by Philip on 05.05.2017.
 */

public class ScoreBoard {

    private final ProjectY screenManager;
   // private BitmapFont font;
    private GameScreen gameScreen;
    private MultiplayerGameScreen multiplayerGameScreen;

    private CustomLabel labelScore, labelPlayer, labelPlayerName, labelPlayerScore;


    public ScoreBoard(Stage stage, ProjectY sm, final GameScreen gameScreen, int score, Player player){
        this.screenManager = sm;
        this.gameScreen=gameScreen;
        init(stage, sm, score, player);
    }

    public ScoreBoard(Stage stage, ProjectY sm, final MultiplayerGameScreen gameScreen, int score, Player player){
        this.screenManager = sm;
        this.multiplayerGameScreen=gameScreen;
        init(stage, sm, score, player);
    }

    private void init(Stage stage, ProjectY sm, int score, Player player){
        final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");


        labelPlayer = new CustomLabel("Player: ");
        labelPlayer.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayer.getWidth()*2/3, Gdx.graphics.getHeight() - labelPlayer.getHeight()-200);

        labelScore = new CustomLabel("Score: ");
        labelScore.setPosition((Gdx.graphics.getWidth() / 2)- labelScore.getWidth()*2/3 +StyleHandler.getLabelWidth()*2/3, Gdx.graphics.getHeight() - labelScore.getHeight()-200);

        labelPlayerName = new CustomLabel(player.getName());
        labelPlayerName.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayerName.getWidth()*2/3 , Gdx.graphics.getHeight() - labelScore.getHeight()-300);

        labelPlayerScore = new CustomLabel(""+score);
        labelPlayerScore.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayerScore.getWidth()*2/3 +StyleHandler.getLabelWidth()*2/3, Gdx.graphics.getHeight() - labelScore.getHeight()-300);

        stage.addActor(labelPlayer);
        stage.addActor(labelScore);
        stage.addActor(labelPlayerName);
        stage.addActor(labelPlayerScore);
    }

    public CustomLabel[] getLabelPlayerLabelScore() {
        CustomLabel[] arr = new CustomLabel[4];
        arr[0] = labelPlayer;
        arr[1] = labelScore;
        arr[2] = labelPlayerName;
        arr[3] = labelPlayerScore;
        return arr;
    }
}
