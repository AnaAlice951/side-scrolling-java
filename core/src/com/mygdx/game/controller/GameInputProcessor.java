package com.mygdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.world.Camera;
import com.mygdx.game.view.world.World;

public class GameInputProcessor implements InputProcessor {
	
	World world;
	Player player;
	
	public GameInputProcessor(World world, Player player) {
		this.world = world;
		this.player = player;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Input.Keys.LEFT) {
			world.getCamera().setMovingLeft(true);
			player.setMovingLeft(true);
		}
		
		if(keycode == Input.Keys.RIGHT) { 
			world.getCamera().setMovingRight(true);
			player.setMovingRight(true);
		}
		
		if (keycode == Input.Keys.X) {
			player.setAttacking(true);
			player.setMovingRight(false);
			player.setMovingLeft(false);
		}
		
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if(keycode == Input.Keys.LEFT) {
			world.getCamera().setMovingLeft(false);
			player.setMovingLeft(false);
		}
		
		if(keycode == Input.Keys.RIGHT) {
			world.getCamera().setMovingRight(false);
			player.setMovingRight(false);	
		}
		
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
