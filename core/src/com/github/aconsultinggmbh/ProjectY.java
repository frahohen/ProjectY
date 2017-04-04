package com.github.aconsultinggmbh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ProjectY extends Game {

	public ProjectY() {
	}

	@Override
	public void create() {

		this.setScreen(new LevelScreen(this));
	}

	@Override
	public void render() {
		super.render(); //important!
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
