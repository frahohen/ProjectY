package com.github.aconsultinggmbh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelScreen implements Screen {

    private final ProjectY screenManager;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private float playerSpeed = 5.0f;
    private float scale = 6.0f;

    private TextureAtlas atlas;
    private Skin skin;
    private TextButton buttonFire;
    private BitmapFont font;

    private Label labelScore;
    private int score;

    public LevelScreen (ProjectY screenManager) {
        this.screenManager = screenManager;
        create();
    }


    private void create(){
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        stage = new Stage(new ScreenViewport(), batch);

        // Button for shooting and Score
        atlas = new TextureAtlas("button.pack");
        skin = new Skin(atlas);

        font = new BitmapFont();
        font.getData().setScale(5.0f);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOn");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        score = 0;
        Label.LabelStyle labelStyle = new Label.LabelStyle( font, Color.WHITE);
        labelScore = new Label("Score: "+score, labelStyle);
        labelScore.setWidth(400);
        labelScore.setPosition(Gdx.graphics.getWidth()- labelScore.getWidth()-40, Gdx.graphics.getHeight() - labelScore.getHeight()-20);

        buttonFire = new TextButton("Fire", textButtonStyle);
        buttonFire.setWidth(300);
        buttonFire.setHeight(120);
        buttonFire.setPosition(20, 20);
        buttonFire.pad(20);
        buttonFire.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUG", "Fire");
                score++;
                labelScore.setText("Score: "+score);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(labelScore);
        stage.addActor(buttonFire);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.314f, 0.314f, 0.314f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Draw stage for touchpad
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    }
}
