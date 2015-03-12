package src.map.editor;

import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Item;

class ItemAdder implements MapAddable {
	private Item item_;
	public ItemAdder(Item item) {
		item_ = item;
	}

	@Override
	public int addToMap(MapMapEditor_Interface mapp_, int x, int y) {
		if(!mapp_.withinMap(x, y)){return 1;}
		return mapp_.addItem(item_,x,y);
	}

}
