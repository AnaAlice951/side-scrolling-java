package com.mygdx.game.graphic.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.graphic.world.TileType;
import com.mygdx.game.models.BreakableObject;
import com.mygdx.game.models.MyGame;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.State;
import com.mygdx.game.models.Zombie;
import com.mygdx.game.models.Drop;
import com.mygdx.game.models.LittleBat;
import com.mygdx.game.graphic.UI.SuperiorInterface;
import com.mygdx.game.graphic.world.World;

public class PlayScreen implements Screen {

	private SpriteBatch batch;
	private World world;
	private SuperiorInterface topBar;
	private State state;
	
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

	private Player player;
	private ArrayList <Zombie> zombies = new ArrayList<Zombie>();
	private ArrayList <LittleBat> littleBats = new ArrayList<LittleBat>();
	private Music backgroundMusic;
	private Music bossBattleMusic;
	private Sound gameOverSound;
	private Sound allClearSound;
	private long lastPlayerDamage;

	public PlayScreen(SpriteBatch batch, MyGame game) {
		this.batch = batch;
	}

	@Override
	public void show() {
		state = new State(2);
		player = new Player(state);
		world = new World(player, state);
		world.loadMap("levelmap.tmx");
		
		zombies.add(new Zombie(state.getStage()));
		littleBats.add(new LittleBat(state.getStage()));
		
		topBar = new SuperiorInterface(world.getGameState(), world.getCamera());
		
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

		handleInput();

		world.render(delta);

		batch.begin();
		for(BreakableObject obj: breakableObjects) {
			if (player.getAttackHitbox().overlaps(obj.getBounds())) {
				obj.breakObject();
			}

			if (player.getPlayerHitbox().overlaps(obj.getDrop().getBounds()) && obj.getDrop().isDropped()) {
				obj.getDrop().collectDrop();
				state.heartsCollected++;
			}
			
			obj.draw(batch);
		}
		
		for(Zombie zombie: zombies) {
			if(player.getAttackHitbox().overlaps(zombie.enemyHitbox) && !zombie.destroyed) {
				zombie.destroy();
				state.score += 10;
			}
			if (player.getPlayerHitbox().overlaps(zombie.enemyHitbox)) {
				if (TimeUtils.millis() - lastPlayerDamage > 500) {
					lastPlayerDamage = TimeUtils.millis();
					if(state.getPlayerLife() -1 == 0) {
						player.setDying(true);
					}
					state.setPlayerLife(state.getPlayerLife() -1);
					
				}
			}
			if(!zombie.destroyed) {
				zombie.move(delta);
				zombie.draw(batch);
			}
			
		}
		
		for(LittleBat littleBat: littleBats) {
			if(player.getAttackHitbox().overlaps(littleBat.enemyHitbox) && !littleBat.destroyed) {
				littleBat.destroy();
				state.score += 10;
			}
			if (player.getPlayerHitbox().overlaps(littleBat.enemyHitbox)) {
				if (TimeUtils.millis() - lastPlayerDamage > 500) {
					lastPlayerDamage = TimeUtils.millis();
					state.setPlayerLife(state.getPlayerLife() -1);
				}
			}
			if(!littleBat.destroyed) {
				littleBat.move(delta);
				littleBat.draw(batch);
			}
		}
		
		topBar.draw(batch);
		applyGravityAndCollisions(delta);
		player.move(delta);
		player.draw(batch);
		batch.end();
	}

	private void applyGravityAndCollisions(float delta) {
		TileType tile = world.getTileTypeByCoordinate(1, (int) (player.getX()/(32*0.8f)), (int) (player.getY()/(32*0.8f)));

		if(tile != null) {
			String tileId = tile.toString();
			if(tileId != "GROUND" && tileId != "STAIR") {
				player.setY(player.getY() - 500 * delta);
			} else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				player.setJumping(true);
			}
		} else {
			player.setY(player.getY() - 500 * delta);
		}

		tile = world.getTileTypeByCoordinate(1, (int) ((player.getX()/(32*0.8f)) + (2*0.8f)), (int) ((player.getY()/(32*0.8f)) + (2*0.8f)));
		if(tile != null) {
			String tileId = tile.toString();
			if(tileId == "GROUND" || tileId == "LITTLE_BRICK") {
				player.setX(player.getX() - 300 * delta);
			}
		}
		
		tile = world.getTileTypeByCoordinate(1, (int) ((player.getX()/(32*0.8f)) + (0.8f)), (int) ((player.getY()/(32*0.8f)) + (2*0.8f)));
		if(tile != null) {
			String tileId = tile.toString();
			if(tileId == "GROUND" || tileId == "LITTLE_BRICK") {
				player.setX(player.getX() + 300 * delta);
			}
		}

	}

	private void handleInput() {
		if(Gdx.input.justTouched()) {
			Vector3 touchPoint = new Vector3();

			for(BreakableObject obj: breakableObjects) {
				world.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

				player.setX(touchPoint.x);
				player.setY(touchPoint.y);
			}
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if(!player.isAttacking()) {
				world.getCamera().setMovingLeft(true);
				player.setMovingLeft(true);
			}
		} else {
			world.getCamera().setMovingLeft(false);
			player.setMovingLeft(false);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if(!player.isAttacking()) {
				world.getCamera().setMovingRight(true);
				player.setMovingRight(true);
			}
		} else {
			world.getCamera().setMovingRight(false);
			player.setMovingRight(false);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.X)) {
			player.setAttacking(true);
			world.getCamera().setMovingLeft(false);
			world.getCamera().setMovingRight(false);
			player.setMovingRight(false);
			player.setMovingLeft(false);
		}
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
		player.dispose();
	}
}
