package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LittleBat{
    private Texture[] frames = new Texture[4];
    private Animation <Texture> animation;
	private boolean flipped = false;
	public boolean destroyed = false;
	private float stateTime = 0f;
	private int life, side;
	private float GAME_UNIT = 32 * 0.8f;
    private int ENEMY_WIDTH = 32;
    private int ENEMY_HEIGHT = 32;
	public Rectangle enemyHitbox;
	public float x,y;
	public int stage;

	public LittleBat(int stage) {
		this.stage = stage;
		
		for(int i = 0; i < 4; i++)
            frames[i] = new Texture(Gdx.files.internal("enemys/bat/fly"+ (i + 1) + ".png"));
        animation = new Animation<Texture>(0.2f, frames);
    
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
		y = GAME_UNIT * 10;
		enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);
	}
	
	public void draw (SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		Texture currentFrame = animation.getKeyFrame(stateTime, true);
		if(!destroyed) 
		batch.draw(
	        currentFrame,
	        x,y,
	        ENEMY_WIDTH, ENEMY_HEIGHT,
	        0, 0,
	        currentFrame.getWidth(),currentFrame.getHeight(),
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
