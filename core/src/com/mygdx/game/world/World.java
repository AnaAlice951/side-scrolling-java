package com.mygdx.game.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.elements.Player;
import com.mygdx.game.State;
import com.mygdx.game.screens.PlayScreen;

/**
 * Classe responsável pela manipulação geral do mundo do jogo
 *
 * Vídeo de HollowBit, no Youtube: LibGDX Platformer Tutorial #2a: Rendering Tiled Maps (Metroidvania Tutorial)
 * @link https://www.youtube.com/watch?v=-ir6O5hS-Qk&list=PLrnO5Pu2zAHIKPZ8o14_FNIp9KVvwPNpn&index=2
 */
public class World {
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private Camera camera;
	private State gameState;
	private Player player;

	private static World instance;

	public World() {
		camera = Camera.getInstance();
		gameState = State.getInstance();
		player = Player.getInstance();
	}

	/**
	 * Retorna uma instância única da classe (Singleton)
	 *
	 * @return instância única
	 */
	public static World getInstance() {
		if(instance == null) {
			synchronized (World.class) {
				if(instance == null) {
					instance = new World();
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
	public World resetInstance() {
		instance = new World();
		return instance;
	}

	/**
	 * Carrega o tilemap no mundo
	 *
	 * @param tmxFile XML tilemal
	 */
	public void loadMap(String tmxFile) {
		tiledMap = new TmxMapLoader().load(tmxFile);
		renderer = new OrthogonalTiledMapRenderer(tiledMap, 0.8f);
	}

	/**
	 * Obtém o tipo do tile através da coordenada
	 *
	 * @param layer camada de tiles
	 * @param col coluna
	 * @param row linha
	 * @return tipo do tile
	 */
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);

		if(cell != null) {
			TiledMapTile tile = cell.getTile();
			
			if(tile != null) {
				int id = tile.getId(); 
				return TileType.getTileTypeById(id);
			}
		}
		
		return null; 
	}

	/**
	 * Renderiza o mundo
	 */
	public void render() {
		camera.updateCamera(gameState.getStage(), player);
		renderer.setView(camera);
		renderer.render();
	}

	/**
	 * Limpa objetos inutilizados da memória
	 */
	public void dispose() {
		tiledMap.dispose();
		renderer.dispose();
	}

	public Camera getCamera() {
		return camera;
	}
}