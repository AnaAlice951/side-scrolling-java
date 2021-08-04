package com.mygdx.game.models;

public class State {
	private int stage;
	private int playerLife = 10;
	public int score = 0, heartsCollected = 0, playerChances = 3;
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

	public State(int stage) {
		this.stage = stage;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
}