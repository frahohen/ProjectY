package com.github.aconsultinggmbh.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.github.aconsultinggmbh.networking.Client;
import com.github.aconsultinggmbh.networking.Server;
import com.github.aconsultinggmbh.networking.ServerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
