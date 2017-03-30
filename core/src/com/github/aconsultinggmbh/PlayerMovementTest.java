package com.github.aconsultinggmbh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerMovementTest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player player;
	TouchKnob touch;
	Stage stage;
	@Override
	public void create () {
		batch = new SpriteBatch();
		initTouchpad();
		initPlayer();

	}

	void initPlayer(){
		player =new Player(100,100);
	}
	void initTouchpad(){
		touch = new TouchKnob(15,15);
		Gdx.input.setInputProcessor(stage);
		stage = new Stage(new ScreenViewport(), batch);
		stage.addActor(touch);
		Gdx.input.setInputProcessor(stage);
		touch.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				float x=((TouchKnob)actor).getKnobPercentX();
				float y=((TouchKnob)actor).getKnobPercentY();
				player.setDirection(new Vector2(x,y));
			}
		});
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update(Gdx.graphics.getDeltaTime());
		batch.begin();
		player.draw(batch);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
