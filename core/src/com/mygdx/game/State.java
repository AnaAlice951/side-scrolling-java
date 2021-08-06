package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class State {
	private int stage;
	private int playerLife = 10;
	private int bossLife = 10;
	private int score = 0, heartsCollected = 0, playerChances = 3;

	private Sound nextStageSound;
	private Sound lifeLost;


	public State(int stage) {
		this.stage = stage;

		nextStageSound = Gdx.audio.newSound(Gdx.files.internal("audio/next_stage.mp3"));
		lifeLost = Gdx.audio.newSound(Gdx.files.internal("audio/life_lost.mp3"));
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		nextStageSound.play();
		this.stage = stage;
	}

	public int getPlayerLife() {
		return playerLife;
	}

	public void setPlayerLife(int playerLife) {
		this.playerLife = playerLife;
		if(playerLife <= 0) {
			playerChances--;
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