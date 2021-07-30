package com.mygdx.game.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.model.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		float SUPERIOR_INTERFACE_HEIGHT_RATIO = 0.09765625f;
		int SCREEN_WIDTH = LwjglApplicationConfiguration.getDesktopDisplayMode().width / 2;
		int SCREEN_HEIGHT = (int) ((LwjglApplicationConfiguration.getDesktopDisplayMode().height / 2) +
				(SCREEN_WIDTH * SUPERIOR_INTERFACE_HEIGHT_RATIO));

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Castlevania";
		config.width = SCREEN_WIDTH;
		config.height = SCREEN_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new MyGame(), config);
	}
}