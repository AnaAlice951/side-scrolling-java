package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameInputProcessor;
import com.mygdx.game.TileType;
import com.mygdx.game.World;

public class PlayScreen implements Screen {

	private World world;
	private GameInputProcessor inputProcessor;
	
	private Music backgroundMusic;
	private Music bossBattleMusic;
	private Sound gameOverSound;
	private Sound allClearSound;
	
	@Override
	public void show() {
		world = new World();
		world.loadMap("levelmap.tmx");
		
		inputProcessor = new GameInputProcessor(world);
		Gdx.input.setInputProcessor(inputProcessor);
		
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background.mp3"));
		backgroundMusic.setLooping(true);
		
		bossBattleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/boss_battle.mp3"));
		bossBattleMusic.setLooping(true);
		
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("audio/game_over.mp3"));
		allClearSound = Gdx.audio.newSound(Gdx.files.internal("audio/all_clear.mp3"));
		
		backgroundMusic.play();
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.justTouched()) {
			
			Vector3 position = world.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			TileType type = world.getTileTypeByCoordinate(2, (int) position.x/32, (int) position.y/32);
			
			if(type != null && type.isBreakable()) {
				Cell cell = world.getCellByCoordinate(2,  (int) position.x/32, (int) position.y/32);
				cell.setTile(null);
			}
		}
		
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
