package src.model.map;

import src.IO_Bundle;
import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;
import src.model.map.constructs.Terrain;

public interface MapMapEditor_Interface {
	public IO_Bundle getMapAt(int x, int y,int width, int height);
	 public int addTerrain(Terrain t, int x, int y);
	 public int addEntity(Entity e, int x, int y);
	 public int addItem(Item i, int x, int y);
	 public boolean withinMap(int x, int y);
}
