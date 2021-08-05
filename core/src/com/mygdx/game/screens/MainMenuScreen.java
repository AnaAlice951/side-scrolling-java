package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;

public class MainMenuScreen implements Screen {
	private MyGame game;
	
	private Animation<Texture> loopAnimation;
	
	private Texture[] loopAnimationFrames = new Texture[9];
	
	private SpriteBatch batch;
	
	private float stateTime;
	
	public MainMenuScreen(SpriteBatch batch, MyGame game) {
		this.batch = batch;
		this.game = game;
		stateTime = 0f;
	}

	@Override
	public void show() {
		for(int i = 0; i < 9; i++)
			loopAnimationFrames[i] = new Texture(Gdx.files.internal("title-screen-frames/loop/Frame ("+ (i+1) +").jpg"));
		
		loopAnimation = new Animation<Texture>(0.05f, loopAnimationFrames);
	}

	@Override
	public void render(float delta) {
		stateTime += Gdx.graphics.getDeltaTime();
		
		Texture currentFrame = loopAnimation.getKeyFrame(stateTime, true);
		batch.begin();
		batch.draw(
			currentFrame, 
			0, 
			0, 
			Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 
			0, 0, 
			600, 338, 
			false, false
		);
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new PlayScreen(batch, game));
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		for(Texture frame : loopAnimationFrames) {
			frame.dispose();
		}
	}
}
