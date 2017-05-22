package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Game;

public class ProjectY extends Game {

	public ProjectY() {
	}

	@Override
	public void create() {
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render() {
		super.render(); //important!
	}

	@Override
	public void dispose() {
        super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
