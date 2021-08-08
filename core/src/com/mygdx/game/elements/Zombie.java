package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

/**
 * Classe responsável pela renderização e manipulação dos zumbis
 */
public class Zombie{
    private Texture frame;
	private boolean flipped;
	private boolean destroyed = false;
	private int side;
    private int ENEMY_WIDTH = 32;
    private int ENEMY_HEIGHT = 96;
	private Rectangle enemyHitbox;
	private float x;
	private float y;

	public Zombie() {

		// define a textura
		frame = new Texture(Gdx.files.internal("enemys/zombie.png"));

		// define qual será o lado de onde o inimigo partirá
		side = (int) Math.round(Math.random());

		if (side==0) {
			x = Constants.GAME_UNIT * 50;
			flipped = true;
		}
		else {
			x = Constants.GAME_UNIT * 150;
			flipped = false;
		}
		y = Constants.GAME_UNIT;

		// define a hitbox
		enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);
	}

	/**
	 * Renderiza o zumbi
	 *
	 * @param batch sprite batch
	 */
	public void draw (SpriteBatch batch) {

		// desenha o zumbi caso não tenha sido derrotado
		if(!destroyed) {
			batch.draw(
				frame,
				x, y,
				ENEMY_WIDTH, ENEMY_HEIGHT,
				0, 0,
				frame.getWidth(), frame.getHeight(),
				flipped, false
			);
		}
	}

	/**
	 * Movimenta o zumbi
	 *
	 * @param delta tempo decorrido
	 */
	public void move(float delta) {

		if (side == 0) {
			x += 150 * delta;
		}
		else {
			x -= 150 * delta;
		}
		enemyHitbox.x = x;
		enemyHitbox.y = y;
		verifyOverflow();
	}

	/**
	 * Deleta o zumbi do jogo
	 */
	public void destroy() {
		destroyed = true;

		// deleta a hitbox
		enemyHitbox.x = 0;
		enemyHitbox.y = 0;
		enemyHitbox.width = 0;
		enemyHitbox.height = 0;
	}

	/**
	 * Verifica se o zumbi está dentro da área permitida
	 */
	public void verifyOverflow() {
		float limit;

		if(side == 0 ) {
			limit = Constants.GAME_UNIT * 150 - ENEMY_WIDTH;
			if(x > limit)
				destroy();
		} else {
			limit = Constants.GAME_UNIT * 50;
			if(x < limit)
				destroy();
		}
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public Rectangle getEnemyHitbox() {
		return enemyHitbox;
	}

	public Texture getFrame() {
		return frame;
	}
}
