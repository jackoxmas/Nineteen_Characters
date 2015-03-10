package src.io.controller;

import java.util.HashMap;

import src.Function;
import src.IO_Bundle;
import src.Key_Commands;
import src.io.view.Viewport;
import src.io.view.display.Display;

public abstract class Controller implements Function<Void,Character> {
	private KeyRemapper remap_;
	private Viewport currentView_;
	private final String userName_;
	public Controller(Viewport view,KeyRemapper remap,String uName) {
		remap_ = remap;
		currentView_ = view;
		userName_ = uName;
		Display.getDisplay().addDirectCommandReceiver(new Function<Void, Key_Commands>() {

			@Override
			public Void apply(Key_Commands foo) {
				takeTurnandPrintTurn(foo);
				return null;
			}

		});
		Display.getDisplay().addGameInputerHandler(this);
		Display.getDisplay().setView(currentView_);
		Display.getDisplay().printView();
	}
	protected Viewport getView(){return currentView_;}
	protected void setView(Viewport view){currentView_=view;}

	protected void takeTurnandPrintTurn(char foo) {
		Key_Commands input = getRemapper().mapInput(foo);
		takeTurnandPrintTurn(input);
	}
	protected abstract void takeTurnandPrintTurn(Key_Commands foo);
	
	public String getUserName() {
		return userName_;
	}

	protected KeyRemapper getRemapper(){return remap_;}
	/**
	 * Gets the underlying key remapping values
	 *
	 * @return A HashMap with the remapped key values in it
	 * @author Alex Stewart
	 */
	public HashMap<Character, Key_Commands> getRemap() {
		if (remap_ == null) {
			return null;
		}
		return remap_.getMap();
	}

	/**
	 * Sets the underlying key remapping
	 *
	 * @param remap The new key remapping to be applied
	 * @author Alex Stewart
	 */
	public void setRemap(HashMap<Character, Key_Commands> remap) {
		if (remap_ == null) {
			remap_ = new GameRemapper();
		}
		remap_.setMap(remap);
	}
	


	public void updateDisplay(IO_Bundle bundle){
		getView().renderToDisplay(bundle);
		Display.getDisplay().setView(getView());
		Display.getDisplay().printView();
	}
	
	@Override
	public Void apply(Character foo) {
		takeTurnandPrintTurn(foo);
		return null;
	}


}
