package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

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
		frame = new Texture(Gdx.files.internal("enemys/zombie.png"));
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
		enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);
	}
	
	public void draw (SpriteBatch batch) {

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
	
	public void destroy() {
		destroyed = true;
		enemyHitbox = new Rectangle (0,0,0,0);
	}

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
