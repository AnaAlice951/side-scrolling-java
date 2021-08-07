package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.MainMenuScreen;

public class MyGame extends Game {
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
}
