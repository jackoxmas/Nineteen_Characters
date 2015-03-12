package src.map.editor;

import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Avatar;

class AvatarAdder implements MapAddable {
	private Avatar Avatar_;
	public AvatarAdder(Avatar ent) {
		Avatar_ = ent;
	}

	@Override
	public int addToMap(MapMapEditor_Interface mapp_, int x, int y) {
		if(isEmpty()){return 2;}
		if(!mapp_.withinMap(x, y)){return 1;}
		int result = mapp_.addAvatar(Avatar_, x, y);
		Avatar_ = null;
		return result;
	}

	@Override
	public boolean isEmpty() {
		return(Avatar_==null);
	}

}
