package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {
	
	private Animation<Texture> animation;
    private Texture[] frames;
	private boolean flipped = false;
	private float stateTime = 0f;
	private int life;
	private float GAME_UNIT = 32 * 0.8f;
    private int ENEMY_WIDTH;
    private int ENEMY_HEIGHT;
	private Rectangle enemyHitbox;
	
	public Enemy(Animation<Texture> animation, Texture[] frames, int life, int ENEMY_WIDTH, int ENEMY_HEIGHT, Rectangle enemyHitbox) {
		this.animation = animation;
		this.frames = frames;
		this.life = life;
		this.ENEMY_WIDTH = ENEMY_WIDTH;
		this.ENEMY_HEIGHT = ENEMY_HEIGHT;
		this.enemyHitbox = enemyHitbox;
	}
	
}
