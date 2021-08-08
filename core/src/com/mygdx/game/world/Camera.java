package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Constants;
import com.mygdx.game.State;
import com.mygdx.game.elements.Player;

/**
 * Classe responsável por definir e manipular a câmera ortográfica do jogo
 *
 * Referências:
 *
 * Vídeo de HollowBit, no Youtube: LibGDX Platformer Tutorial #2a: Rendering Tiled Maps (Metroidvania Tutorial)
 * @link https://www.youtube.com/watch?v=-ir6O5hS-Qk&list=PLrnO5Pu2zAHIKPZ8o14_FNIp9KVvwPNpn&index=2
 */
public class Camera extends OrthographicCamera {

	private boolean movingRight = false;
	private boolean movingLeft = false;
	
	private float leftLimit;
	private float rightLimit;

	// quantidade de tiles por largura da tela
	private float TILES_PER_DEVICE_WIDTH = Gdx.graphics.getWidth() / (32 * 0.8f);

	private static Camera instance;

	private Camera() {
		setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Retorna uma instância única da classe (Singleton)
	 *
	 * @return instância única
	 */
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

	/**
	 * Redimensiona o viewport
	 *
	 * @param vw largura do viewport
	 * @param vh altura do viewport
	 */
	public void resizeViewport(int vw, int vh) {
		viewportWidth = vw;
		viewportHeight = vh;
	}

	/**
	 * Atualiza a câmera
	 *
	 * @param stage fasea atual
	 * @param player instância do ogador
	 */
	public void updateCamera(int stage, Player player) {
		super.update();
		position.x = player.getX();
		verifyOverflow(stage);
	}

	/**
	 * Verifica se a posição da câmera está dentro dos limites permitidos
	 *
	 * @param stage fase atual
	 */
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

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
}