package src.map.editor;

import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Terrain;

class TerrainAdder implements MapAddable {
	private Terrain terrain_;
	public TerrainAdder(Terrain terra) {
		terrain_ = terra;
	}

	@Override
	public int addToMap(MapMapEditor_Interface mapp_, int x, int y) {
		if(!mapp_.withinMap(x, y)){return 1;}
		return mapp_.addTerrain(terrain_, x, y);
	}

}
