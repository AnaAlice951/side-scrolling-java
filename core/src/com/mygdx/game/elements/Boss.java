package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

/**
 * Classe responsável por renderizar e manipular informações referentes ao Final Boss
 */
public class Boss{
    private Texture[] frames = new Texture[2];
    private Animation <Texture> animation;
	private float stateTime = 0f;

    private int ENEMY_WIDTH = 95;
    private int ENEMY_HEIGHT = 55;
	private Rectangle enemyHitbox;

	public boolean destroyed = false;
	private float x = (225 * Constants.GAME_UNIT);
	private float y = (15 * Constants.GAME_UNIT);

	private Sound killBoss;

	private static Boss instance;

	private Boss() {

		// define os frames e a animação
		for(int i = 0; i < 2; i++)
            frames[i] = new Texture(Gdx.files.internal("enemys/boss/bat"+ (i + 1) + ".png"));
        animation = new Animation<Texture>(0.2f, frames);

		// define a hitbox
        enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);

        // define os sons
        killBoss = Gdx.audio.newSound(Gdx.files.internal("audio/kill_boss.mp3"));
	}

	/**
	 * Retorna uma instância única da classe (Singleton)
	 *
	 * @return instância única da classe
	 */
	public static Boss getInstance() {
		if(instance == null) {
			synchronized (Boss.class) {
				if(instance == null) {
					instance = new Boss();
				}
			}
		}

		return instance;
	}

	/**
	 * Redefine a instância do Boss para o estado inicial
	 *
	 * @return instância redefinida
	 */
	public Boss resetInstance() {
		instance = new Boss();
		return instance;
	}

	/**
	 * Renderiza o Boss
	 *
	 * @param batch sprite batch
	 */
	public void draw (SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		Texture currentFrame = animation.getKeyFrame(stateTime, true);

		// renderiza o frame atual da animação caso o Boss não tenha sido derrotado
		if(!destroyed) {
			batch.draw(
				currentFrame,
				x, y,
				ENEMY_WIDTH, ENEMY_HEIGHT,
				0, 0,
				currentFrame.getWidth(), currentFrame.getHeight(),
				false, false
			);
		}
	}

	/**
	 * Movimenta do Boss
	 *
	 * Referências:
	 *
	 * Tópico "Java: Enemy follow Player" no StackOverflow
	 * @link https://stackoverflow.com/questions/25128545/java-enemy-follow-player
	 *
	 * @param player instância do jogador
	 * @param delta tempo decorrido
	 */
	public void move(Player player, float delta) {
		float diffX = player.getX() - x;
		float diffY = player.getY() - y;
		float angle = (float) Math.atan2(diffY, diffX);

		x += 100 * delta * Math.cos(angle);
		y += 100 * delta * Math.sin(angle);
		enemyHitbox.x = x;
		enemyHitbox.y = y;
	}

	/**
	 * Deleta o Boss do jogo
	 */
	public void destroy() {
		killBoss.play();
		destroyed = true;

		// deleta a hitbox
		enemyHitbox = new Rectangle (0,0,0,0);
	}

	public Texture[] getFrames() {
		return frames;
	}

	public Rectangle getEnemyHitbox() {
		return enemyHitbox;
	}
}
