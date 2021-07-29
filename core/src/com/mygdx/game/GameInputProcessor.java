package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {
	
	World world;
	
	public GameInputProcessor(World world) {
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Input.Keys.LEFT) 
			world.getCamera().setMovingLeft(true); // mexer o player ao inves da camera e atualizar a camera centralizando nele
		
		
		if(keycode == Input.Keys.RIGHT) 
			world.getCamera().setMovingRight(true);
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if(keycode == Input.Keys.LEFT)
			world.getCamera().setMovingLeft(false);
		
		if(keycode == Input.Keys.RIGHT) 
			world.getCamera().setMovingRight(false);
		
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
