package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

public class Drop {
	private String object;
	private int x;
	private int y;
	private float width;
	private float height;
	private Texture dropTexture;
	private Rectangle bounds;
	private boolean dropped = false;
	private boolean dropCollected = false;
	private Sound collectHeartSound;

	public Drop(String object, int x, int y) {
		this.object = object;
		this.x = x;
		this.y = y;
		
		if(this.object == "heart") {
			width = 48 * 0.8f;
			height = 48 * 0.8f;

			dropTexture = new Texture(Gdx.files.internal("items/heart.png"));

			bounds = new Rectangle(Constants.GAME_UNIT * x, Constants.GAME_UNIT * y, 32, 32);
			
			collectHeartSound = Gdx.audio.newSound(Gdx.files.internal("audio/collecting_heart.mp3"));
		}
	}
	
	public void draw(SpriteBatch batch) {
		if(!dropCollected) {
			if(object == "heart") {
				batch.draw(
					dropTexture,
					Constants.GAME_UNIT * x, Constants.GAME_UNIT * y,
					width, height,
					0, 0, 
					dropTexture.getWidth(), dropTexture.getHeight(),
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
		collectHeartSound.play();
	}

	public void dispose() {
		this.dropTexture.dispose();
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