package com.mygdx.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Constants;

/**
 * Classe responsável pela renderização e manipulação de informações referentes aos objetos quebráveis
 */
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

	public BreakableObject(String object, int x, int y, Drop drop) {
		this.object = object;
		this.x = x;
		this.y = y;
		this.drop = drop;

		// verifica o tipo de objeto quebrável e define seus atributos e sprites de acordo com o tipo
		if(object == "firejar") {

			// define o tamanho e a hitbox
			width = 48 * 0.8f;
			height = 96 * 0.8f;

			assetOriginalWidth = 15;
			assetOriginalHeight = 32;

			bounds = new Rectangle(Constants.GAME_UNIT * x, Constants.GAME_UNIT * y, width, height);

			// define os frames da animação
			for(int i = 0; i < 2; i++)
				frames[i] = new Texture(Gdx.files.internal("items/firejar/firejar" + (i + 1) + ".png"));
		} else if(object == "candle") {
			// define o tamanho e a hitbox
			width = 32 * 0.8f;
			height = 64 * 0.8f;

			assetOriginalWidth = 8;
			assetOriginalHeight = 16;

			bounds = new Rectangle(Constants.GAME_UNIT * x, Constants.GAME_UNIT * y, width, height);

			// define os frames da animação
			for(int i = 0; i < 2; i++)
				frames[i] = new Texture(Gdx.files.internal("items/candle/candle"+ (i + 1) + ".png"));
		}

		// define a animação
		animation = new Animation<Texture>(0.1f, frames);
	}

	/**
	 * Renderiza o objeto quebrável
	 *
	 * @param batch sprite batch
	 */
	public void draw(SpriteBatch batch) {

		// renderiza o objeto caso não tenha sido quebrado, do contrário, renderiza seu drop
		if(!broken) {
			stateTime += Gdx.graphics.getDeltaTime();
			Texture currentFrame = animation.getKeyFrame(stateTime, true);
			
			if(object == "firejar") {
				batch.draw(
					currentFrame,
						Constants.GAME_UNIT * x, Constants.GAME_UNIT * y,
					width, height,
					0, 0, 
					assetOriginalWidth, assetOriginalHeight,
					false, false
				);
			} else if(object == "candle") {
				batch.draw(
					currentFrame,
						Constants.GAME_UNIT * x, Constants.GAME_UNIT * y,
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

	/**
	 * Deleta o objeto quebrável do jogo
	 */
	public void breakObject() {
		broken = true;
		drop.setDropped(true);

		// deleta a hitbox
		bounds.height = 0;
		bounds.width = 0;
		bounds.x = 0;
		bounds.y = 0;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Drop getDrop() {
		return drop;
	}

	/**
	 * Limpa os objetos inutilizados da memória
	 */
	public void dispose() {
		for(Texture frame: frames)
			frame.dispose();
	}
}