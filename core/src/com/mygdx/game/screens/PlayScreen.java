package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.GameInputProcessor;
import com.mygdx.game.World;

public class PlayScreen implements Screen {

	private World world;
	private GameInputProcessor inputProcessor;
	
	@Override
	public void show() {
		world = new World();
		world.loadMap("levelmap.tmx");
		
		inputProcessor = new GameInputProcessor(world);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		world.getCamera().resizeViewport(width, height);
		world.getCamera().update();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		world.dispose();
	}
}
