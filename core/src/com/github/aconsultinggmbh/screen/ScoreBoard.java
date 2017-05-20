package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;


/**
 * Created by Philip on 05.05.2017.
 */

public class ScoreBoard {

    private final ProjectY screenManager;
    private BitmapFont font;
    private GameScreen gameScreen;
    private ArrayList<Label> labels = new ArrayList<Label>();

    private Label labelScore, labelPlayer, labelPlayerName, labelPlayerScore;


    public ScoreBoard(ProjectY sm, final GameScreen gameScreen){
        this.screenManager = sm;

        this.gameScreen=gameScreen;

        final Preferences settings = Gdx.app.getPreferences("ProjectY_settings");
        font = new BitmapFont();
        font.getData().setScale(5.0f);

        Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);
        labelPlayer = new Label("Player: ", labelStyle);
        labelPlayer.setWidth(400);
        labelPlayer.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayer.getWidth()+50, Gdx.graphics.getHeight() - labelPlayer.getHeight()-200);

        labelScore = new Label("Score: ", labelStyle);
        labelScore.setWidth(400);
        labelScore.setPosition((Gdx.graphics.getWidth() / 2)- labelScore.getWidth() +500, Gdx.graphics.getHeight() - labelScore.getHeight()-200);


        labels.add(labelPlayer);
        labels.add(labelScore);
    }

    public void addPlayer(String playerName, int score){

        font = new BitmapFont();
        font.getData().setScale(5.0f);

        Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);

        int size = 2;
        int height = 300;               //height of added labels
        while(labels.size() > size){
            height = height + 100;
            size = size + 2;
        }

        labelPlayerName = new Label(playerName, labelStyle);
        labelPlayerName.setWidth(400);
        labelPlayerName.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayerName.getWidth() +50, Gdx.graphics.getHeight() - labelScore.getHeight()-height);

        labelPlayerScore = new Label(""+score, labelStyle);
        labelPlayerScore.setWidth(400);
        labelPlayerScore.setPosition((Gdx.graphics.getWidth() / 2)- labelPlayerScore.getWidth() +500, Gdx.graphics.getHeight() - labelScore.getHeight()-height);

        labels.add(labelPlayerName);
        labels.add(labelPlayerScore);
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }
    public void showScoreboard(Stage stage){
        for(int i = 0; i < labels.size(); i++){
            stage.addActor(labels.get(i));
        }
    }
}
