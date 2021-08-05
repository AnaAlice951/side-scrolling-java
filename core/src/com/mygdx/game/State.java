package com.mygdx.game;

public class State {
	private int stage;
	private int playerLife = 10;
	private int score = 0, heartsCollected = 0, playerChances = 3;

	public State(int stage) {
		this.stage = stage;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getPlayerLife() {
		return playerLife;
	}

	public void setPlayerLife(int playerLife) {
		this.playerLife = playerLife;
		if(playerLife <= 0) {
			playerChances--;
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
}