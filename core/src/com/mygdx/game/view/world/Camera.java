package com.mygdx.game.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
	
	private boolean movingRight = false;
	private boolean movingLeft = false;
	
	private int leftLimit;
	private int rightLimit;
	
	public Camera() {
		setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void resizeViewport(int vw, int vh) {
		viewportWidth = vw;
		viewportHeight = vh;
	}
	
	public void updateCamera(float delta, int stage) {
		super.update();
		
		if(isMovingRight()) {
			position.x += 200 * delta;
		} else if(isMovingLeft()) {
			position.x -= 200 * delta;
		}
		
		verifyOverflow(stage);
	}
	
	public void verifyOverflow(int stage) {
		if(stage == 1) {
			leftLimit = 640;
			rightLimit = 960;
		} else if (stage == 2 || stage == 3) {
			leftLimit = 2240;
			rightLimit = 4160;
		} else if(stage == 4) {
			leftLimit = 5440;
			rightLimit = 5760;
		} else if(stage == 5) {
			leftLimit = 7040;
			rightLimit = 7360;
		}

		if(position.x <= leftLimit)
			position.x = leftLimit;
		
		if(position.x >= rightLimit)
			position.x = rightLimit;
	}
	
	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
}