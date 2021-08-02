package com.mygdx.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.model.BreakableObject;
import com.mygdx.game.model.MyGame;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Drop;
import com.mygdx.game.controller.GameInputProcessor;
import com.mygdx.game.view.UI.SuperiorInterface;
import com.mygdx.game.view.world.World;

public class PlayScreen implements Screen {

	private SpriteBatch batch;
	private World world;
	private SuperiorInterface topBar;
	
	private BreakableObject[] breakableObjects = {
		new BreakableObject("firejar", 10, 1, new Drop("heart", 10, 1)),
		new BreakableObject("firejar", 20, 1, new Drop("heart", 20, 1)),
		new BreakableObject("firejar", 30, 1, new Drop("heart", 30, 1)),
		new BreakableObject("firejar", 40, 1, new Drop("heart", 40, 1)),
		new BreakableObject("candle", 58, 2, new Drop("heart", 58, 1)),
		new BreakableObject("candle", 68, 2, new Drop("heart", 68, 1)),
		new BreakableObject("candle", 78, 2, new Drop("heart", 78, 1)),
		new BreakableObject("candle", 92, 8, new Drop("heart", 92, 7)),
		new BreakableObject("candle", 106, 11, new Drop("heart", 106, 10)),
		new BreakableObject("candle", 98, 2, new Drop("heart", 98, 1)),
		new BreakableObject("candle", 116, 2, new Drop("heart", 116, 1)),
		new BreakableObject("candle", 136, 2, new Drop("heart", 136, 1)),
		new BreakableObject("candle", 144, 2, new Drop("heart", 144, 1)),
		new BreakableObject("candle", 140, 14, new Drop("heart", 140, 13)),
		new BreakableObject("candle", 153, 2, new Drop("heart", 153, 1)),
		new BreakableObject("candle", 162, 2, new Drop("heart", 162, 1)),
		new BreakableObject("candle", 174, 2, new Drop("heart", 174, 1)),
		new BreakableObject("candle", 185, 2, new Drop("heart", 185, 1)),
		new BreakableObject("candle", 193, 2, new Drop("heart", 193, 1)),
		new BreakableObject("candle", 159, 13, new Drop("heart", 159, 12)),
		new BreakableObject("candle", 168, 7, new Drop("heart", 168, 5)),
		new BreakableObject("candle", 180, 7, new Drop("heart", 180, 5)),
		new BreakableObject("candle", 190, 13, new Drop("heart", 190, 12)),
		new BreakableObject("candle", 202, 14, new Drop("heart", 202, 13)),
		new BreakableObject("candle", 203, 2, new Drop("heart", 203, 1)),
		new BreakableObject("candle", 211, 10, new Drop("heart", 211, 9)),
		new BreakableObject("candle", 211, 2, new Drop("heart", 211, 1)),
		new BreakableObject("candle", 222, 2, new Drop("heart", 222, 1)),
		new BreakableObject("candle", 232, 2, new Drop("heart", 232, 1)),
		new BreakableObject("candle", 244, 2, new Drop("heart", 244, 1))
	};

	private GameInputProcessor inputProcessor;
	private Player player;
	private Music backgroundMusic;
	private Music bossBattleMusic;
	private Sound gameOverSound;
	private Sound allClearSound;
	
	public PlayScreen(SpriteBatch batch, MyGame game) {
		this.batch = batch;
	}
	
	
	@Override
	public void show() {
		world = new World();
		world.loadMap("levelmap.tmx");
		player = new Player();
		
		topBar = new SuperiorInterface(world.getGameState(), world.getCamera());
		
		inputProcessor = new GameInputProcessor(world, player);
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
		
		batch.setProjectionMatrix(world.getCamera().combined);

		// substituir clique por colisão no momento de quebrar os objetos
		if(Gdx.input.justTouched()) {
			Vector3 touchPoint = new Vector3();
			
			for(BreakableObject obj: breakableObjects) {
				    world.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
				    
				    if(obj.getDrop().isDropped() && obj.getDrop().getBounds().contains(touchPoint.x, touchPoint.y))
				    	obj.getDrop().collectDrop();
				    
				    
				    if(obj.getBounds().contains(touchPoint.x, touchPoint.y))
				    	obj.breakObject();
			}
		}
		
		world.render(delta);
		batch.begin();
		for(BreakableObject obj: breakableObjects) {
			obj.draw(batch);
		}
		topBar.draw(batch);
		player.move(delta, world.getGameState().getStage());
		player.draw(batch);
		batch.end();
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
		backgroundMusic.dispose();
		bossBattleMusic.dispose();
		gameOverSound.dispose();
		allClearSound.dispose();
		topBar.dispose();
		for(BreakableObject obj: breakableObjects) {
			obj.dispose();
		}
	}
}
