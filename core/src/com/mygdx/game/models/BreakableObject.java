package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BreakableObject {
	private Animation<Texture> animation;
	private Texture[] frames = new Texture[2];
	private float stateTime = 0f;
	private String object;
	private Drop drop;
	private int x;
	private int y;
	private float width;
	private float height;
	private int assetOriginalWidth;
	private int assetOriginalHeight;
	private Rectangle bounds;
	private boolean broken = false;

	private float GAME_UNIT = 32 * 0.8f;

	public BreakableObject(String object, int x, int y, Drop drop) {
		this.object = object;
		this.x = x;
		this.y = y;
		this.drop = drop;
		
		if(object == "firejar") {
			width = 48 * 0.8f;
			height = 96 * 0.8f;

			assetOriginalWidth = 15;
			assetOriginalHeight = 32;

			bounds = new Rectangle(GAME_UNIT * x, GAME_UNIT * y, width, height);
			
			for(int i = 0; i < 2; i++)
				frames[i] = new Texture(Gdx.files.internal("items/firejar/firejar" + (i + 1) + ".png"));
		} else if(object == "candle") {
			width = 32 * 0.8f;
			height = 64 * 0.8f;

			assetOriginalWidth = 8;
			assetOriginalHeight = 16;

			bounds = new Rectangle(GAME_UNIT * x, GAME_UNIT * y, width, height);
			
			for(int i = 0; i < 2; i++)
				frames[i] = new Texture(Gdx.files.internal("items/candle/candle"+ (i + 1) + ".png"));
		}
		
		animation = new Animation<Texture>(0.1f, frames);
	}
	
	public void draw(SpriteBatch batch) {
		if(!broken) {
			stateTime += Gdx.graphics.getDeltaTime();
			Texture currentFrame = animation.getKeyFrame(stateTime, true);
			
			if(object == "firejar") {
				batch.draw(
					currentFrame, 
					GAME_UNIT * x, GAME_UNIT * y,
					width, height,
					0, 0, 
					assetOriginalWidth, assetOriginalHeight,
					false, false
				);
			} else if(object == "candle") {
				batch.draw(
					currentFrame, 
					GAME_UNIT * x, GAME_UNIT * y,
					width, height,
					0, 0,
						assetOriginalWidth, assetOriginalHeight,
					false, false
				);
			}
		} else {
			drop.draw(batch);
		}
	}
	
	public void breakObject() {
		this.broken = true;
		this.drop.setDropped(true);
		this.bounds.height = 0;
		this.bounds.width = 0;
		this.bounds.x = 0;
		this.bounds.y = 0;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public Drop getDrop() {
		return drop;
	}
	
	public void dispose() {
		for(Texture frame: frames)
			frame.dispose();
	}
}