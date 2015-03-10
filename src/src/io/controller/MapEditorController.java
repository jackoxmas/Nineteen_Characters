package src.io.controller;

import src.Key_Commands;
import src.io.view.MapEditorView;
import src.model.map.MapMapEditor_Interface;

public class MapEditorController extends Controller {
	public MapMapEditor_Interface map_;
	public MapEditorController(MapMapEditor_Interface map) {
		super(new MapEditorView(),new MapEditRemapper());
		map_ = map;
		this.takeTurnandPrintTurn('5');
	}


	int x = 0;
	int y = 0;
	
	@Override
	protected void takeTurnandPrintTurn(Key_Commands foo) {
		switch(foo){
		case MOVE_UP: ++y; break;
		case MOVE_DOWN: --y; break;
		case MOVE_LEFT: --x; break;
		case MOVE_RIGHT: ++x; break;
		default: break;
		
		}
		updateDisplay(map_.getMapAt(x, y, getView().getWidth()/2, getView().getHeight()/2));
		
	}

}
