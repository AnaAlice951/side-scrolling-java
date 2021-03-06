package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Batcher;
import com.mygdx.game.world.TileType;
import com.mygdx.game.elements.Boss;
import com.mygdx.game.elements.BreakableObject;
import com.mygdx.game.MyGame;
import com.mygdx.game.elements.Player;
import com.mygdx.game.State;
import com.mygdx.game.elements.Zombie;
import com.mygdx.game.elements.Drop;
import com.mygdx.game.elements.LittleBat;
import com.mygdx.game.components.SuperiorInterface;
import com.mygdx.game.world.World;

/**
 * Classe responsável por renderizar e manipular informações relacionadas à tela onde ocorre o jogo
 */
public class PlayScreen implements Screen {

	private Batcher batch;
	private World world;
	private SuperiorInterface topBar;
	private State state;
	private MyGame game;

	// define todos os objetos quebrados e seus drops
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
	private Boss boss;
	private ArrayList <Zombie> zombies = new ArrayList<Zombie>();
	private ArrayList <LittleBat> littleBats = new ArrayList<LittleBat>();

	private long lastPlayerDamage;
	private long lastBossHit;
	private long lastEnemySpawn;

	private Music backgroundMusic;
	private Music bossBattleMusic;
	private Sound damage;

	public PlayScreen(MyGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = Batcher.getInstance().resetInstance();
		state = State.getInstance().resetInstance();
		player = Player.getInstance().resetInstance();
		boss = Boss.getInstance().resetInstance();
		world = World.getInstance().resetInstance();

		// carrega o tilemap no mundo
		world.loadMap("levelmap.tmx");
		
		// define uma nova barra superior de UI
		topBar = new SuperiorInterface();

		// define os áudios de background default e luta final, além do som para quando o jogador recebe dano
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background.mp3"));
		backgroundMusic.setLooping(true);
		
		bossBattleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/boss_battle.mp3"));
		bossBattleMusic.setLooping(true);

		damage = Gdx.audio.newSound(Gdx.files.internal("audio/hit.mp3"));

		// toca o som background default
		backgroundMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(world.getCamera().combined);

		// verifica o input do usuário
		handleInput();

		// renderiza o mapa e atualiza câmera
		world.render();

		batch.begin();

		// spawna inimigos de periodicamente
		spawnEnemies(state.getStage());

		// renderiza todos os objetos quebráveis e monitora seu estado
		for(BreakableObject obj: breakableObjects) {
			if (player.getAttackHitbox().overlaps(obj.getBounds())) {
				obj.breakObject();
			}

			if (player.getPlayerHitbox().overlaps(obj.getDrop().getBounds()) && obj.getDrop().isDropped()) {
				obj.getDrop().collectDrop();
				state.setHeartsCollected(state.getHeartsCollected() + 1);
			}
			
			obj.draw(batch);
		}

		// renderiza todos os zumbis e monitora seu estado
		for(Zombie zombie: zombies) {
			if(player.getAttackHitbox().overlaps(zombie.getEnemyHitbox()) && !zombie.isDestroyed()) {
				zombie.destroy();
				state.setScore(state.getScore() + 10);
			}
			if (player.getPlayerHitbox().overlaps(zombie.getEnemyHitbox())) {
				if (TimeUtils.millis() - lastPlayerDamage > 2000) {
					lastPlayerDamage = TimeUtils.millis();
					damage.play();

					if(state.getPlayerLife() -1 == 0) {
						player.setDying(true);
					}

					state.setPlayerLife(state.getPlayerLife() -1);
				}
			}
			if(!zombie.isDestroyed()) {
				zombie.move(delta);
				zombie.draw(batch);
			}

		}

		// renderiza todos os morcegos e monitora seu estado
		for(LittleBat littleBat: littleBats) {
			if(player.getAttackHitbox().overlaps(littleBat.enemyHitbox) && !littleBat.destroyed) {
				littleBat.destroy();
				state.setScore(state.getScore() + 10);
			}

			if (player.getPlayerHitbox().overlaps(littleBat.enemyHitbox)) {
				if (TimeUtils.millis() - lastPlayerDamage > 2000) {
					lastPlayerDamage = TimeUtils.millis();
					damage.play();

					if(state.getPlayerLife() -1 == 0) {
						player.setDying(true);
					}

					state.setPlayerLife(state.getPlayerLife() -1);
				}
			}

			if(!littleBat.destroyed) {
				littleBat.move(delta);
				littleBat.draw(batch);
			}
		}


		// renderiza op Boss e monitora seu estado
		if(player.getAttackHitbox().overlaps(boss.getEnemyHitbox()) && !boss.destroyed) {
			if (TimeUtils.millis() - lastBossHit > 500) {
				lastBossHit = TimeUtils.millis();
				state.setBossLife(state.getBossLife() - 1);

				if (state.getBossLife() == 0) {
					bossBattleMusic.stop();
					boss.destroy();
					state.setScore(state.getScore() + 1000);
				}
			}
		}

		if (player.getPlayerHitbox().overlaps(boss.getEnemyHitbox())) {
			if (TimeUtils.millis() - lastPlayerDamage > 2000) {
				lastPlayerDamage = TimeUtils.millis();
				damage.play();

				if(state.getPlayerLife() -1 == 0) {
					player.setDying(true);
				}

				state.setPlayerLife(state.getPlayerLife() -1);
			}
		}

		if(!boss.destroyed && state.getStage() == 5) {
			boss.draw(batch);
			boss.move(player, delta);
		}

		// monitora a quantidade de chances restantes para o jogador
		if(state.getPlayerChances() == 0) {
			game.setScreen(new GameOverScreen(game));
		}

		// renderiza a barra superior
		topBar.draw();

		// aplica a gravidade no jogador e verifica colisões com tiles
		applyGravityAndCollisions(delta);

		// move o jogador
		player.move(delta);

		// muda as cores do sprite batch para que o sprite do jogador "pisque" ao receber dano
		long timeElapsedFromHit = TimeUtils.millis() - lastPlayerDamage;
		if(timeElapsedFromHit < 2000) {
			if(timeElapsedFromHit > 200 && timeElapsedFromHit <= 400 ||
			timeElapsedFromHit > 600 && timeElapsedFromHit <= 800 ||
			timeElapsedFromHit > 1000 && timeElapsedFromHit <= 1200 ||
			timeElapsedFromHit > 1400 && timeElapsedFromHit <= 1600 ||
			timeElapsedFromHit > 1800 && timeElapsedFromHit < 2000)
				batch.setColor(0, 0, 0, 0);
		}

		// renderiza o jogador
		player.draw(batch);

		batch.setColor(1, 1, 1, 1);
		batch.end();
	}

	/**
	 * Aplica a gravidade ao jogador e detecta colisões com os tiles do mapa
	 *
	 * @param delta tempo decorrido
	 */
	private void applyGravityAndCollisions(float delta) {
		TileType tile = world.getTileTypeByCoordinate(1, (int) (player.getX()/(32*0.8f)), (int) (player.getY()/(32*0.8f)));

		if(tile != null) {
			String tileId = tile.toString();

			// verifica se o jogador está em solo ou no ar
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

			// verifica se o jogador está avançando horizontalmente para um bloco rígido na direita
			if(tileId == "GROUND" || tileId == "LITTLE_BRICK") {
				player.setX(player.getX() - 300 * delta);

				// verifica se o jogador avançou para o próximo nível
			} else if (tileId == "VOID") {
				state.setStage(state.getStage() + 1);

				// verifica se o jogador está no último nível
				if(state.getStage() == 5){

					// para a música de fundo default e passa a tocar a música da luta final
					backgroundMusic.stop();
					bossBattleMusic.play();
				}

				// verifica se o jogador completou as fases
				if(state.getStage() > 5) {
					game.setScreen(new AllClearScreen(game));
				}
			}
		}
		
		tile = world.getTileTypeByCoordinate(1, (int) ((player.getX()/(32*0.8f)) + (0.8f)), (int) ((player.getY()/(32*0.8f)) + (2*0.8f)));

		if(tile != null) {
			String tileId = tile.toString();

			// verifica se o jogador está avançando horizontalmente para um bloco rígido na esquerda
			if(tileId == "GROUND" || tileId == "LITTLE_BRICK") {
				player.setX(player.getX() + 300 * delta);
			}
		}
	}

	/**
	 * Recebe e gerencia os inputs do usuário
	 */
	private void handleInput() {
		// verifica movimento para a esquerda
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if(!player.isAttacking()) {
				world.getCamera().setMovingLeft(true);
				player.setMovingLeft(true);
			}
		} else {
			world.getCamera().setMovingLeft(false);
			player.setMovingLeft(false);
		}

		// verifica movimento para a direita
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if(!player.isAttacking()) {
				world.getCamera().setMovingRight(true);
				player.setMovingRight(true);
			}
		} else {
			world.getCamera().setMovingRight(false);
			player.setMovingRight(false);
		}

		// verifica comando de ataque
		if(Gdx.input.isKeyPressed(Input.Keys.X)) {
			player.setAttacking(true);
			world.getCamera().setMovingLeft(false);
			world.getCamera().setMovingRight(false);
			player.setMovingRight(false);
			player.setMovingLeft(false);
		}
	}

	/**
	 * Spawna inimigos no mapa periodicamente, em seu respectivo nível
	 *
	 * @param stage fase atual
	 */
	public void spawnEnemies(int stage) {
		if (TimeUtils.millis() - lastEnemySpawn > 2000) {
			lastEnemySpawn = TimeUtils.millis();

			if(stage == 2 || stage == 3)
				zombies.add(new Zombie());

			if(stage == 4)
				littleBats.add(new LittleBat());
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
		topBar.dispose();

		for(BreakableObject obj: breakableObjects) {
			obj.dispose();
			obj.getDrop().dispose();
		}

		for(Zombie zombie: zombies)
			zombie.getFrame().dispose();

		for(LittleBat littleBat: littleBats)
			for(Texture t: littleBat.getFrames())
				t.dispose();

		for(Texture t: boss.getFrames())
			t.dispose();

		state.dispose();
		player.dispose();
	}
}
