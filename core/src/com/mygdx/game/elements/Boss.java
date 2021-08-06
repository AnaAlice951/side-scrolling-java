package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

public class Boss{
    private Texture[] frames = new Texture[2];
    private Animation <Texture> animation;
	private float stateTime = 0f;

    private int ENEMY_WIDTH = 95;
    private int ENEMY_HEIGHT = 55;
	public Rectangle enemyHitbox;

	public boolean destroyed = false;
	private float x = (225 * Constants.GAME_UNIT);
	private float y = (15 * Constants.GAME_UNIT);

	public Boss() {
		for(int i = 0; i < 2; i++)
            frames[i] = new Texture(Gdx.files.internal("enemys/boss/bat"+ (i + 1) + ".png"));
        animation = new Animation<Texture>(0.2f, frames);
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
				false, false
			);
		}
	}
	
	
	public void move(Player player, float delta) {
		float diffX = player.getX() - x;
		float diffY = player.getY() - y;
		float angle = (float) Math.atan2(diffY, diffX);

		x += 100 * delta * Math.cos(angle);
		y += 100 * delta * Math.sin(angle);
		enemyHitbox.x = x;
		enemyHitbox.y = y;
	}

	public void destroy() {
		destroyed = true;
		enemyHitbox = new Rectangle (0,0,0,0);
	}

	public Texture[] getFrames() {
		return frames;
	}
}
