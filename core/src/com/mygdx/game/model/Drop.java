package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Drop {
	private String object;
	private int x;
	private int y;
	private int width;
	private int height;
	private int assetOriginalWidth;
	private int assetOriginalHeight;
	private Texture dropTexture;
	private Rectangle bounds;
	private boolean dropped = false;
	private boolean dropCollected = false;

	private final int GAME_UNIT = 32;

	public Drop(String object, int x, int y) {
		this.object = object;
		this.x = x;
		this.y = y;
		
		if(this.object == "heart") {
			width = 48;
			height = 48;

			assetOriginalWidth = 16;
			assetOriginalHeight = 15;

			dropTexture = new Texture(Gdx.files.internal("items/heart.png"));

			bounds = new Rectangle(GAME_UNIT * x, GAME_UNIT * y, 32, 32);
		}
	}
	
	public void draw(SpriteBatch batch) {
		if(!dropCollected) {
			if(object == "heart") {
				batch.draw(
					dropTexture,
					GAME_UNIT * x, GAME_UNIT * y,
					width, height,
					0, 0, 
					assetOriginalWidth, assetOriginalHeight,
					false, false
				);
			}
		}
	}


	public void collectDrop() {
		this.dropCollected = true;
		this.bounds.height = 0;
		this.bounds.width = 0;
		this.bounds.x = 0;
		this.bounds.y = 0;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}
}