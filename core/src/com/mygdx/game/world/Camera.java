package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Constants;
import com.mygdx.game.State;
import com.mygdx.game.elements.Player;

public class Camera extends OrthographicCamera {
	
	private boolean movingRight = false;
	private boolean movingLeft = false;
	
	private float leftLimit;
	private float rightLimit;

	private float TILES_PER_DEVICE_WIDTH = Gdx.graphics.getWidth() / (32 * 0.8f);

	private static Camera instance;

	private Camera() {
		setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public static Camera getInstance() {
		if(instance == null) {
			synchronized (Camera.class) {
				if(instance == null) {
					instance = new Camera();
				}
			}
		}

		return instance;
	}

	public Camera resetInstance() {
		instance = new Camera();
		return instance;
	}

	public void resizeViewport(int vw, int vh) {
		viewportWidth = vw;
		viewportHeight = vh;
	}
	
	public void updateCamera(float delta, int stage, Player player) {
		super.update();
		position.x = player.getX();
		verifyOverflow(stage);
	}
	
	public void verifyOverflow(int stage) {
		leftLimit = (Gdx.graphics.getWidth()/2) + (Constants.GAME_UNIT * 50 * (stage - 1));
		rightLimit = leftLimit + (Constants.GAME_UNIT * (50 - TILES_PER_DEVICE_WIDTH));

		if(stage == 2 || stage == 3 ) {
			leftLimit = (Gdx.graphics.getWidth()/2) + (Constants.GAME_UNIT * 50);
			rightLimit = leftLimit + (Constants.GAME_UNIT * (100 - TILES_PER_DEVICE_WIDTH));
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