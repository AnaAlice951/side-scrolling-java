package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

public class LittleBat{
    private Texture[] frames = new Texture[4];
    private Animation <Texture> animation;
	private float stateTime = 0f;

	private int side;
    private int ENEMY_WIDTH = 32;
    private int ENEMY_HEIGHT = 32;
	public Rectangle enemyHitbox;

	private boolean flipped;
	public boolean destroyed = false;
	private float x;
	private float y;

	public LittleBat() {
		for(int i = 0; i < 4; i++)
            frames[i] = new Texture(Gdx.files.internal("enemys/bat/fly"+ (i + 1) + ".png"));
        animation = new Animation<Texture>(0.2f, frames);
    
		side = (int) Math.round(Math.random());

		if (side==0) {
			x = Constants.GAME_UNIT * 150;
			flipped = true;
		}
		else {
			x = Constants.GAME_UNIT * 200;
			flipped = false;
		}
		y = Constants.GAME_UNIT * 10;
		enemyHitbox = new Rectangle (x, y,ENEMY_WIDTH ,ENEMY_HEIGHT);
	}
	
	public void draw (SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		Texture currentFrame = animation.getKeyFrame(stateTime, true);
		if(!destroyed) {
			batch.draw(
				currentFrame,
				x, y,
				ENEMY_WIDTH, ENEMY_HEIGHT,
				0, 0,
				currentFrame.getWidth(), currentFrame.getHeight(),
				flipped, false
			);
		}
	}
	
	public void move(float delta) {
		if (side == 0) {
			x += 150 * delta;
			if(y < Constants.GAME_UNIT * 3) {
				y += 50 * delta;
			} else {
				y -= 50 * delta;
			}
		}
		else {
			x -= 150 * delta;
			if(y < Constants.GAME_UNIT * 3) {
				y += 50 * delta;
			} else {
				y -= 50 * delta;
			}
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
			limit = Constants.GAME_UNIT * 200 - ENEMY_WIDTH;
			if(x > limit)
				destroy();
		} else {
			limit = Constants.GAME_UNIT * 150;
			if(x < limit)
				destroy();
		}
	}

	public Texture[] getFrames() {
		return frames;
	}
}
