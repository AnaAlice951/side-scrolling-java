package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Zombie{
    private Texture frame;
	private boolean flipped = false;
	public boolean destroyed = false;
	private float stateTime = 0f;
	private int life, side;
	private float GAME_UNIT = 32 * 0.8f;
    private int ENEMY_WIDTH = 32;
    private int ENEMY_HEIGHT = 96;
	public Rectangle enemyHitbox;
	public float x,y;
	public int stage;

	public Zombie(int stage) {
		this.stage = stage;
		frame = new Texture(Gdx.files.internal("enemys/zombie.png"));
		side = (int) (Math.random());
		if (side==0) {
			x = (GAME_UNIT * 50) * (stage - 1); 
			flipped = true;
			System.out.print(stage);
		}
		else {
			x = (GAME_UNIT * (stage -1)) + (GAME_UNIT * 50);
			flipped = false;
			System.out.print(side);
		}
		y = GAME_UNIT;
		enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);
	}
	
	public void draw (SpriteBatch batch) {
		if(!destroyed) 
		batch.draw(
	        frame,
	        x,y,
	        ENEMY_WIDTH, ENEMY_HEIGHT,
	        0, 0,
	        frame.getWidth(), frame.getHeight(),
	        flipped, false  
		);
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
		verifyOverflow(stage);
	}
	
	public void destroy() {
		destroyed = true;
		enemyHitbox = new Rectangle (0,0,0,0);
	}
	
	 public void verifyOverflow(int stage) {
	    	float leftLimit = (GAME_UNIT * 50 * (stage - 1) - 20);
			float rightLimit = leftLimit + (GAME_UNIT * 50 - (ENEMY_WIDTH - 10));

			if(stage == 2 || stage == 3 ) {
				leftLimit = (GAME_UNIT * 50) -20;
				rightLimit = leftLimit + (GAME_UNIT * 100 - (ENEMY_WIDTH - 10));
			}

			if(x <= leftLimit)
				destroy();

			if(x >= rightLimit)
				destroy();
		}
	
}
