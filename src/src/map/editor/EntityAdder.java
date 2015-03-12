package src.map.editor;

import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Entity;

class EntityAdder implements MapAddable {
	private Entity entity_;
	public EntityAdder(Entity ent) {
		entity_ = ent;
	}

	@Override
	public int addToMap(MapMapEditor_Interface mapp_, int x, int y) {
		if(!mapp_.withinMap(x, y)){return 1;}
		return mapp_.addEntity(entity_, x, y);
	}

}
