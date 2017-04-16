package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Game;
import com.github.aconsultinggmbh.screen.GameScreen;

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
        super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
