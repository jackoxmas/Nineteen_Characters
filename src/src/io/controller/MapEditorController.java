package src.io.controller;

import src.Key_Commands;
import src.io.view.MapEditorView;

public class MapEditorController extends Controller {

	public MapEditorController() {
		super(new MapEditorView(),new MapEditRemapper());
	}



	
	@Override
	protected void takeTurnandPrintTurn(Key_Commands foo) {
		// TODO Auto-generated method stub
		
	}

}
