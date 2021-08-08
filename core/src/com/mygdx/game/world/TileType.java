package com.mygdx.game.world;

import java.util.HashMap;

/**
 * Enum respnsável por categorizar os tiles utilizados na construção do mapa
 *
 * Referências:
 *
 * Vídeo de HollowBit, no Youtube: LibGDX Platformer Tutorial #2a: Rendering Tiled Maps (Metroidvania Tutorial)
 * @link https://www.youtube.com/watch?v=-ir6O5hS-Qk&list=PLrnO5Pu2zAHIKPZ8o14_FNIp9KVvwPNpn&index=2
 */
public enum TileType {
	BRICK(1),
	CANDLE(2),
	DARK_BRICK(3),
	GROUND(4),
	WALL(5),
	LITTLE_BRICK(6),
	SKY(7),
	STAIR(8),
	VOID(9),
	WINDOW(10),
	JAR(11);

	private int id;

	TileType(int id) {
		this.id = id;
	}

	/**
	 * Retorna o ID do tile
	 *
	 * @return ID do tile
	 */
	public int getId() {
		return id;
	}

	// Hash que conecta o tipo do tile a um chave inteira
	private static HashMap<Integer, TileType> tileHashMap;
	
	static {
		tileHashMap = new HashMap<Integer, TileType>();
		for(TileType tileType : TileType.values()) {
			tileHashMap.put(tileType.getId(), tileType);
		}
	}

	// obtém o tipo do tile por ID
	public static TileType getTileTypeById(int id) {
		return tileHashMap.get(id);
	}
}
