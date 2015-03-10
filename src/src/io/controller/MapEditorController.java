package src.io.controller;

import java.util.ArrayList;

import src.Key_Commands;
import src.io.view.MapEditorView;
import src.io.view.display.Display;
import src.model.map.MapMapEditor_Interface;
import src.model.map.constructs.Item;
import src.model.map.constructs.Monster;
import src.model.map.constructs.OneHandedSword;
import src.model.map.constructs.Terrain;

public class MapEditorController extends Controller {
	private MapMapEditor_Interface map_;
	private MapEditorView mappy_viewy_ = new MapEditorView();
	private ArrayList<String> spawnables_ = new ArrayList<String>();
	private String lastSpawned = "";
	public MapEditorController(MapMapEditor_Interface map) {
		super(new MapEditorView(),new MapEditRemapper());
		super.setView(mappy_viewy_);
		map_ = map;

		spawnables_.add("Item");
		spawnables_.add("Terrain");
		spawnables_.add("Entity");
		mappy_viewy_.setSpawnableList(spawnables_);
		this.takeTurnandPrintTurn('5');
		Display.getDisplay().setMessage("SWITCH TO THE INVENTORY TAB!!!");
		Display.getDisplay().setMessage("TO USE: Hit space to spawn something. Select what to spawn by " +
				"clicking on it in the item box. Move around as usual.");
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
		case MAP_INSERT: mapInsert(Display.getDisplay().getHighlightedItem());
		default: break;

		}
		mappy_viewy_.setSpawnableList(spawnables_);
		updateDisplay(map_.getMapAt(x, y, getView().getWidth()/2, getView().getHeight()/2));

	}

	private void mapInsert(String spawnName) {
		if(spawnName == null){spawnName = lastSpawned;}
				switch(spawnName){
				case "Item":
					Item onehandedsword = new OneHandedSword("Excalibur", '|');
					map_.addItem(onehandedsword,x,y);
					break;
				case "Terrain":
					Terrain obstacle = new Terrain("land", '▨', false, false);
					map_.addTerrain(obstacle, x, y);
					break;
				case "Entity" :
					Monster monster = new Monster("monster1", '웃');
					monster.getStatsPack().increaseQuantityOfExperienceBy(300);
					map_.addEntity(monster, x, y);
					break;
				}
				setLastSpawned(spawnName);
		}
		private  void setLastSpawned(String spawnName){
			mappy_viewy_.setLastSpawned(spawnName);
			lastSpawned = spawnName;
		}

	}
