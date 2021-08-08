package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Classe responsável por manipular todo o estado do jogo e dos personagens
 */
public class State {
	// fase atual
	private int stage;

	// vida do jogador na chance atual
	private int playerLife = 10;

	// vida do Boss final
	private int bossLife = 10;

	// pontuação total do jogador
	private int score = 0;

	// quantidade de corações coletados
	private int heartsCollected = 0;

	// quantidade total de chances restantes
	private int playerChances = 3;

	private Sound nextStageSound;
	private Sound lifeLost;

	private static State instance;

	private State(int stage) {
		this.stage = stage;

		// define o som que toca ao passar de fase
		nextStageSound = Gdx.audio.newSound(Gdx.files.internal("audio/next_stage.mp3"));

		// define o som que toca ao perder uma chance
		lifeLost = Gdx.audio.newSound(Gdx.files.internal("audio/life_lost.mp3"));
	}

	/**
	 * Retorna uma instância única da classe (Singleton)
	 *
	 * @return instância única
	 */
	public static State getInstance() {
		if(instance == null) {
			synchronized (State.class) {
				if(instance == null) {
					instance = new State(1);
				}
			}
		}

		return instance;
	}

	/**
	 * Redefine a instância para o estado inicial
	 *
	 * @return instância redefinida
	 */
	public State resetInstance() {
		instance = new State(1);
		return instance;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		// toca o som de próxima fase
		nextStageSound.play();
		this.stage = stage;
	}

	public int getPlayerLife() {
		return playerLife;
	}

	public void setPlayerLife(int playerLife) {
		this.playerLife = playerLife;

		// verifica se a vida do jogador chegou a zero e contabiliza as chances respectivamente
		if(playerLife <= 0) {
			playerChances--;
			if(playerChances != 0)

				// toca o som de chance perdida
				lifeLost.play();
			this.playerLife = 10;
		}
	}

	public int getPlayerChances() {
		return playerChances;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHeartsCollected() {
		return heartsCollected;
	}

	public void setHeartsCollected(int heartsCollected) {
		this.heartsCollected = heartsCollected;
	}

	public int getBossLife() {
		return bossLife;
	}

	public void setBossLife(int bossLife) {
		this.bossLife = bossLife;
	}

	public void dispose() {
		nextStageSound.dispose();
	}
}