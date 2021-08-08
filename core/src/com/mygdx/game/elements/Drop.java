package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

/**
 * Classe responsável pela renderização e manipulação de informações referentes aos drops
 */
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

		// define o tamanho e a hitbox
		width = 48 * 0.8f;
		height = 48 * 0.8f;
		bounds = new Rectangle(Constants.GAME_UNIT * x, Constants.GAME_UNIT * y, 32, 32);

		// define a textura do drop
		dropTexture = new Texture(Gdx.files.internal("items/heart.png"));

		// define o som de coletar o drop
		collectHeartSound = Gdx.audio.newSound(Gdx.files.internal("audio/collecting_heart.mp3"));
	}

	/**
	 * Renderiza o drop
	 * @param batch sprite batch
	 */
	public void draw(SpriteBatch batch) {

		// renderiza o drop caso este não tenha sido coletado
		if(!dropCollected) {
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

	/**
	 * Deleta o drop do jogo e contabiliza
	 */
	public void collectDrop() {
		this.dropCollected = true;

		// deleta a hitbox
		this.bounds.height = 0;
		this.bounds.width = 0;
		this.bounds.x = 0;
		this.bounds.y = 0;

		// toca o som de coletar drop
		collectHeartSound.play();
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

	public void dispose() {
		this.dropTexture.dispose();
	}
}