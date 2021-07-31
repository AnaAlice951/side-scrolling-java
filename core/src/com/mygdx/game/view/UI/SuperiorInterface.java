package com.mygdx.game.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.world.Camera;
import com.mygdx.game.model.State;

public class SuperiorInterface {
	private State state;
	private Camera camera;
	private Texture superiorBar = new Texture(Gdx.files.internal("superior.png"));
	private Texture[] lifeBar = new Texture[10];
	private TextComponent text = new TextComponent(25);

	private int GAME_WIDTH = Gdx.graphics.getWidth();
	private int GAME_HEIGHT = Gdx.graphics.getHeight();

	public SuperiorInterface(State state, Camera camera) {
		this.state = state;
		this.camera = camera;
		
		for(int i = 0; i < 10; i++) {
			lifeBar[i] = new Texture(Gdx.files.internal("lifebar/" + (i+1) + "life.png"));
		}
	}
	
	public void draw(Batch batch) {
		batch.draw(
			superiorBar, 
			(int) camera.position.x - GAME_WIDTH/2,
			GAME_HEIGHT - GAME_WIDTH * 0.09765625f,
			GAME_WIDTH, GAME_WIDTH * 0.09765625f,
			0, 0,
			1280, 125,
			false, false
		);
		
		batch.draw(
			lifeBar[0],
			 camera.position.x - GAME_WIDTH/2 + GAME_WIDTH * 0.13671875f,
			GAME_HEIGHT - GAME_WIDTH * 0.0859375f,
			GAME_WIDTH * 0.15625f, GAME_WIDTH * 0.0234375f,
			0, 0, 
			200, 30, 
			false, false
		);
		
		batch.draw(
			lifeBar[0],
			(int) camera.position.x - GAME_WIDTH/2 + GAME_WIDTH * 0.421875f,
			GAME_HEIGHT - GAME_WIDTH * 0.0859375f,
			GAME_WIDTH * 0.15625f, GAME_WIDTH * 0.0234375f,
			0, 0,
			200, 30,
			false, false
		);
		
		text.write(
			(SpriteBatch) batch,
			(int) ((camera.position.x - GAME_WIDTH/2) + GAME_WIDTH * 0.15625f),
			(int) (GAME_HEIGHT - GAME_WIDTH * 0.01953125f),
			"100000",
			Color.WHITE
		);
		
		text.write(
			(SpriteBatch) batch,
			(int) ((camera.position.x - GAME_WIDTH/2) + GAME_WIDTH * 0.7265625f),
			(int) (GAME_HEIGHT - GAME_WIDTH * 0.0234375f),
			"200",
			Color.WHITE
		);
		
		text.write(
			(SpriteBatch) batch,
			(int) ((camera.position.x - GAME_WIDTH/2) + GAME_WIDTH * 0.73046875f),
			(int) (GAME_HEIGHT - GAME_WIDTH * 0.0703125),
			"10000",
			Color.WHITE
		);
	}
	
	public void dispose() {
		superiorBar.dispose();
		text.dispose();
	}
}