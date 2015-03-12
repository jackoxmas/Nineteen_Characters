package src.map.editor;

import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Item;

class DoorKeyAdder implements MapAddable {
	private Item key_;
	private Item door_;
	public DoorKeyAdder(Item key, Item door) {
		key_ = key;
		door_ = door;
	}

	@Override
	public int addToMap(MapMapEditor_Interface mapp_, int x, int y) {
	if(isEmpty()){return 2;}
	if(!mapp_.withinMap(x, y)){return 1;}
	int result;
	if(key_ == null){
		result = mapp_.addItem(door_, x, y);
		door_ = null;
	}else{
		System.out.println(key_);
		result = mapp_.addItem(key_,x,y);
		key_ = null;
	}

	return result;
	}

	@Override
	public boolean isEmpty() {
		return (door_ == null && key_ == null);

	}

}
