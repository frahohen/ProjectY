package com.github.aconsultinggmbh.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.aconsultinggmbh.PlayerMovementTest;
import com.github.aconsultinggmbh.ProjectY;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new ProjectY(), config);
		new LwjglApplication(new PlayerMovementTest(), config); //für playermovement - tests und programmierung
	}
}
