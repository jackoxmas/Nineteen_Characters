package src.map.editor;
import src.AddableThingEnum;
import src.enumHandler;

public class MapAddableFactory {

	public MapAddableFactory() {
		// TODO Auto-generated constructor stub
	}
	public MapAddable getAddable(String addable){
		return getAddable(enumHandler.stringCommandToAddable(addable));
	}
	public MapAddable getAddable(AddableThingEnum addable){
		return null;
	}

}
