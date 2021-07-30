package com.mygdx.game.view.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.model.State;

public class World {
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private Camera camera;
	private State gameState;
	
	public World() {
		camera = new Camera();
		gameState = new State(1);
	}
	
	public void loadMap(String tmxFile) {
		tiledMap = new TmxMapLoader().load(tmxFile);
		renderer = new OrthogonalTiledMapRenderer(tiledMap, 1280/720);
	}
	
	public Cell getCellByCoordinate(int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);
		return cell;
	}
	
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
	
	public void render(float delta) {
		camera.updateCamera(delta, gameState.getStage());
		renderer.setView(camera);
		renderer.render();
	}
	
	public void dispose() {
		tiledMap.dispose();
		renderer.dispose();
	}

	public Camera getCamera() {
		return camera;
	}

	public State getGameState() {
		return gameState;
	}
}