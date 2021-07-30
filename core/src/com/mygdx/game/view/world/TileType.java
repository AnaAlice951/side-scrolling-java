package com.mygdx.game.view.world;

import java.util.HashMap;

public enum TileType {
	BRICK(1, false, false, "Brick"),
	CANDLE(2, false, true, "Candle"),
	DARK_BRICK(3, false, false, "Dark Brick"),
	GROUND(4, true, false, "Default Ground"),
	WALL(5, false, false, "Default Wall"),
	LITTLE_BRICK(6, true, false, "Little Brick"),
	SKY(7, false, false, "Sky"),
	STAIR(8, true, false, "Stair"),
	VOID(9, true, false, "Void"),
	WINDOW(10, false, false, "Window"),
	JAR(11, false, true, "Jar");
	
	private int id;
	private boolean collidable;
	private boolean breakable;
	private String name;
	
	private TileType(int id, boolean collidable, boolean breakable, String name) {
		this.id = id;
		this.collidable = collidable;
		this.breakable = breakable;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean breakable) {
		this.breakable = breakable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private static HashMap<Integer, TileType> tileHashMap;
	
	static {
		tileHashMap = new HashMap<Integer, TileType>();
		for(TileType tileType : TileType.values()) {
			tileHashMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById(int id) {
		return tileHashMap.get(id);
	}
}
