package com.github.aconsultinggmbh;

import com.badlogic.gdx.Game;

public class ProjectY extends Game {

	public ProjectY() {
	}

	@Override
	public void create() {

		this.setScreen(new MainMenuScreen(this));
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
