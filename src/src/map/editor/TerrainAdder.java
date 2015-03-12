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
		if(isEmpty()){return 2;}
		if(!mapp_.withinMap(x, y)){return 1;}
		int result = mapp_.addTerrain(terrain_, x, y);
		terrain_ = null;
		return result;
	}

	@Override
	public boolean isEmpty() {
		return (terrain_ == null);
	}

}
